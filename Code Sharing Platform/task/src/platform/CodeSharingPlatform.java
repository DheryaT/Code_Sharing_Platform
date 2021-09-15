package platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@SpringBootApplication
@Controller
public class CodeSharingPlatform {

    @Autowired
    private CodeRepository repo;

    private static Code code = new Code();

    private static ArrayList<Snippet> snippets = new ArrayList<Snippet>();


    public static void main(String[] args) {
        code.setCode("");
        SpringApplication.run(CodeSharingPlatform.class, args);
    }

    @GetMapping("/code")
    private String getCode(){
        return "<html>\n" +
                "<head>\n" +
                "    <title>Code</title>\n" +
                "</head>\n" +

                "<body>\n" +"<span id=\"load_date\">"+makeDate()+"</span>"
                +
                "    <pre id=\"code_snippet\">\n" + code.getCode() +
                "</pre>\n" +
                "</body>\n" +
                "</html>";
    }

    //@GetMapping("/api/code")
    //private Map<String, String> getApiCode(){
    //    return Map.of("code", code.getCode(), "date", makeDate());
    //}

    @GetMapping("/code/new")
    @ResponseBody
    public String getNewCode(){
        return "<html>\n" +
                "<head>\n" +
                "    <title>Create</title>\n" +
                "</head>\n" +
                "<body>\n" + "<textarea id=\"code_snippet\"> ... </textarea>"
                + "<form>"+ "<button id=\"send_snippet\" type=\"submit\" onclick=\"send()\">Submit</button>" +
                "</form>"+
                "<script>"+
                "function send() {\n" +
                "    let object = {\n" +
                "        \"code\": document.getElementById(\"code_snippet\").value\n" +
                "    };\n" +
                "    \n" +
                "    let json = JSON.stringify(object);\n" +
                "    \n" +
                "    let xhr = new XMLHttpRequest();\n" +
                "    xhr.open(\"POST\", '/api/code/new', false)\n" +
                "    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');\n" +
                "    xhr.send(json);\n" +
                "    \n" +
                "    if (xhr.status == 200) {\n" +
                "      alert(\"Success!\");\n" +
                "    }\n" +
                "}"
                +"</script>"+
                "</body>\n" +
                "</html>";
    }

    @PostMapping("/api/code/new")
    @ResponseBody
    public Map<String, String> newCode(@RequestBody Code newCode){
        Snippet snip = new Snippet(newCode.getCode(), makeDate());
        repo.save(snip);
        return Map.of("id", String.valueOf(snip.getId()));
    }

    @GetMapping("/api/code/{id}")
    @ResponseBody
    private Map<String, String> getApiCodeId(@PathVariable int id){
        Optional<Snippet> snippet = repo.findById(id);
        if (snippet.isPresent()) {
            Snippet snip = snippet.get();
            return Map.of("code", snip.getCode(), "date", snip.getDate());
        }

        return null;
    }

    @GetMapping("/code/{id}")
    @ResponseBody
    private String getCodeId(@PathVariable int id){
        Optional<Snippet> snippet = repo.findById(id);
        if (snippet.isPresent()) {
            Snippet snip = snippet.get();

            return "<html>\n" +
                    "<head>\n" +
                    "    <title>Code</title>\n" +"<link rel=\"stylesheet\"\n" +
                    "       href=\"//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css\">\n" +
                    "<script src=\"//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js\"></script>\n" +
                    "<script>hljs.initHighlightingOnLoad();</script>"+
                    "</head>\n" +

                    "<body>\n" +"<span id=\"load_date\">"+snip.getDate()+"</span>"
                    +
                    "    <pre id=\"code_snippet\">\n<code>" + snip.getCode() +
                    "</code></pre>\n" +
                    "</body>\n" +
                    "</html>";

        }

        return null;

    }

    @GetMapping("/api/code/latest")
    @ResponseBody
    private ArrayList<Map<String, String>> getApiCodeLatest(){
        ArrayList<Map<String, String>> latest = new ArrayList<>();
        ArrayList<Snippet> top10 = new ArrayList<Snippet>(repo.findTop10ByOrderByIdDesc());

        for(Snippet snippet: top10) {
            latest.add(Map.of("code", snippet.getCode(), "date", snippet.getDate()));
        }
        return latest;
    }


    @GetMapping("/code/latest")
    private String getCodeLatest(Model model){
        ArrayList<Map<String, String>> latest = new ArrayList<>();
        ArrayList<Snippet> top10 = new ArrayList<Snippet>(repo.findTop10ByOrderByIdDesc());

        for(Snippet snippet: top10) {
            latest.add(Map.of("code", snippet.getCode(), "date", snippet.getDate()));
        }

        model.addAttribute("codeSnippets", latest);
        return "template";
    }




    public String makeDate(){
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        return formattedDate.toString();
    }



}
