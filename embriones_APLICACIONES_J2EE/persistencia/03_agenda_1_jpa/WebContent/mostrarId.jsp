<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="entidades.Contacto"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Visualizar contacto por email</title>
</head>
<%
	Contacto ct = (Contacto) request.getAttribute("contacto");
%>
<body>
	<center>
	<br/><br/>
	<h2>El contacto encontrado por el id: <%=request.getAttribute("idContacto") %> es:</h2>
	<table border=1>

		<tr><td>Nombre:	<%=ct.getNombre() %></td></tr>
		<tr><td>Email: 	<%=ct.getEmail() %></td></tr>
		<tr><td>telefono: <%=ct.getTelefono() %></td></tr>
	</table>
	<br/><br/>
	<a href="Controller?op=toInicio">Volver</a>
	</center>
</body>
</html>