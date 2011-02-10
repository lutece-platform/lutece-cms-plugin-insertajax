<%@ page errorPage="ErrorPage.jsp" %>

<jsp:useBean id="linkajax" scope="session" class="fr.paris.lutece.plugins.insertajax.web.InsertAjaxInsertServiceJspBean" />

<% 
     response.sendRedirect( linkajax.doInsertLink( request ) );
%>
