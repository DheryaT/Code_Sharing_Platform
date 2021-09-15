<html lang="en">
<head>
    <link rel="stylesheet"
           href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
    <title>Latest</title>
</head>
<body>
<table>
    <#list codeSnippets as codeSnip>
        <tr>
            <td><span>${codeSnip.date}</span></td>
            <td><pre><code>${codeSnip.code}asdjalskd</code></pre></td>
        </tr>
    </#list>
</table>
</body>
</html>