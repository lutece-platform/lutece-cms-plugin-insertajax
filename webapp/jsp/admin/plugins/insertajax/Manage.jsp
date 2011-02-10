<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<jsp:useBean id="insertajax" scope="session" class="fr.paris.lutece.plugins.insertajax.web.InsertAjaxJspBean" />

<% insertajax.init( request, insertajax.RIGHT_MANAGE ); %>
<%= insertajax.getManage( request ) %>

<%@ include file="../../AdminFooter.jsp" %>