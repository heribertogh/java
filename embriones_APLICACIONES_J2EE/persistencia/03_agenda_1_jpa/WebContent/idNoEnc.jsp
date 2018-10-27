<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Email no encontrado</title>
</head>
<body>
	<center>
	<br/><br/><br/><br/><br/>
	<h1>El id <%=request.getAttribute("idContacto") %> no exite en la base de datos</h1><br/><br/>
	<a href="Controller?op=toInicio">Volver</a>
	</center>
</body>
</html>