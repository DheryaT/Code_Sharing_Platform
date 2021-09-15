<html lang="en">
<head>
    <title>Latest</title>
</head>
<body>
<table>
    <#list codeSnippets as codeSnip>
        <tr>
            <td><span>${codeSnip.date}</span></td>
            <td><pre>${codeSnip.code.code}</pre></td>
        </tr>
    </#list>
</table>
</body>
</html>