<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page isELIgnored="false" %>


<html>
    <div>
    <head>
        <title><spring:message code="label.manr"/></title>
    </head>

    <body>
        <h2 style="display: inline-block;"><spring:message code="label.manr"/></h2> 
        <div style="float: right;display: inline-block;"> 
            <a href="/role/list?lang=en">[EN]</a>
            <a href="/role/list?lang=wry">[WRY]</a>
        </div>

        <form action="/logout" style="float: right;position:absolute;right:10px;top:40px;">
            <input type="submit" value="Log Out" />
        </form>
    </div>
         <hr/>
        <div>
            <form:form action="/role/save" method="POST" commandName="roles">
            <table border = "1" width = "80%" id="roleTable" align="center">
                <col width="66%">
                <col width="12%">
                <col width="12%">
                <tr bgcolor = "#D3D3D3">
                    <th><a href =  "/addRole?sortby=<c:out value='role'/>"><spring:message code="label.rolename"/></a></th>
                    <th><spring:message code="label.update"/></th>
                    <th><spring:message code="label.delete"/></th>
                </tr>

                <c:forEach items = "${roleList}" var = "r">
                        <tr>
                                <td> ${r.role}
                                </td>
                                <td>
                                    <a href = "/role/update?roleid=<c:out value='${r.roleId}'/>" ><spring:message code="label.uprole"/></a>
                                </td>
                                <td>
                                    <a href = "/role/delete?roleid=<c:out value='${r.roleId}'/>" ><spring:message code="label.delrole"/></a>
                                </td>
                        </tr>
                </c:forEach>
                <tr>
                        <td>
                            <form:input path="role" style="width:100%;"/>
                            <form:errors class="error" path="role"/><br/>
                        </td>
                        <td align="center" colspan="2">
                            <input style="width:100%;" type="submit" value="<spring:message code='label.addbutton'/>"/>
                        </td>
                        
                        
                <tr>
            </table>
            </form:form>
        </div>
        <br>

        <button onclick="location.href='/'" type="button"><spring:message code="label.back"/></button>


    </body>
</html>