<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Prog.kiev.ua</title>
    </head>
    <body>
    <div align="center">

        <form action="/deleteAll" method="post">
            <table align="center">
                <c:forEach var="pic" items="${map}">
                    <tr>
                        <td><input type="checkbox" name="check" value="${pic.key}"/></td>
                        <td><c:out value="${pic.key}"/></td>
                        <td><a href="/photo/${pic.key}"><img height="100" width="100" src="/photo/${pic.key}"/></a></td>
                    </tr>
                </c:forEach>
            </table>
        <input type="submit" value="Delete"/>
        </form>
    <input type="submit" value="Upload New" onclick="window.location='/';" />
    </div>
    </body>
</html>