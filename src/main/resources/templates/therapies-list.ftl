<html>
<head>
    <title>Therapias</title>
</head>
<body>
<h1>Terapias </h1>
<table>
    <thead>
    <tr>
        <td>ID</td>
        <td>Nombre</td>
        <td>Descripción</td>
    </tr>
    </thead>
    <tbody>
    <#list therapies as therapy>
        <tr>
            <td>${therapy.id}</td>
            <td>${therapy.name}</td>
            <td>${therapy.description}</td>
        </tr>
    </#list>
    </tbody>
</table>
</body>
</html>
