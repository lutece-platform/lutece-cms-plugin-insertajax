/*
 * Copyright (c) 2002-2017, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.insertajax.web;

import fr.paris.lutece.plugins.insertajax.business.InsertAjax;
import fr.paris.lutece.plugins.insertajax.business.InsertAjaxHome;
import fr.paris.lutece.portal.business.role.RoleHome;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.service.workgroup.AdminWorkgroupService;
import fr.paris.lutece.portal.web.admin.PluginAdminPageJspBean;
import fr.paris.lutece.portal.web.constants.Messages;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.html.Paginator;
import fr.paris.lutece.util.url.UrlItem;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * adminFeature for insertAjax plugin
 */
public class InsertAjaxJspBean extends PluginAdminPageJspBean
{
    // Right
    public static final String RIGHT_MANAGE = "INSERTAJAX_MANAGEMENT";

    // properties for page titles
    private static final String PROPERTY_PAGE_TITLE_INSERTAJAX_LIST = "ajax.manage_insertajax.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY = "insertajax.modify_insertajax.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE = "insertajax.create_insertajax.pageTitle";

    // Properties
    private static final String PROPERTY_DEFAULT_INSERTAJAX_LIST_PER_PAGE = "insertajax.insertAjaxList.itemsPerPage";

    // Messages
    private static final String MESSAGE_CONFIRM_REMOVE_INSERTAJAX = "insertajax.message.confirmRemoveInsertAjax";

    // Markers
    private static final String MARK_LIST_INSERTAJAX_LIST = "insertajax_list";
    private static final String MARK_NB_ITEMS_PER_PAGE = "nb_items_per_page";
    private static final String MARK_WORKGROUPS_LIST = "workgroups_list";
    private static final String MARK_WEBAPP_URL = "webapp_url";
    private static final String MARK_LOCALE = "locale";
    private static final String MARK_HTML_CONTENT = "html_content";
    private static final String MARK_INSERTAJAX = "insertajax";
    private static final String MARK_ROLES_LIST = "roles_list";
    private static final String MARK_PAGINATOR = "paginator";

    // parameters
    private static final String PARAMETER_INSERTAJAX_ID = "id";
    private static final String PARAMETER_INSERTAJAX_NAME = "name";
    private static final String PARAMETER_INSERTAJAX_HTML = "html_content";
    private static final String PARAMETER_INSERTAJAX_SQL = "sql";
    private static final String PARAMETER_INSERTAJAX_WORKGROUP = "workgroup";
    private static final String PARAMETER_INSERTAJAX_ROLE = "role";
    private static final String PARAMETER_ID_INSERTAJAX_LIST = "insertajax_list_id";
    private static final String PARAMETER_PAGE_INDEX = "page_index";

    // templates
    private static final String TEMPLATE_MANAGE_INSERTAJAX = "/admin/plugins/insertajax/manage.html";
    private static final String TEMPLATE_CREATE_INSERTAJAX = "/admin/plugins/insertajax/create.html";
    private static final String TEMPLATE_MODIFY_INSERTAJAX = "/admin/plugins/insertajax/modify.html";
    private static final String TEMPLATE_JAVASCRIPT = "admin/plugins/insertajax/insertajax_javascript.html";

    // Jsp Definition
    private static final String JSP_DO_REMOVE_INSERTAJAX = "jsp/admin/plugins/insertajax/DoRemove.jsp";
    private static final String JSP_REDIRECT_TO_MANAGE_INSERTAJAX = "Manage.jsp";

    //Variables
    private int _nDefaultItemsPerPage;
    private String _strCurrentPageIndex;
    private int _nItemsPerPage;

    /**
     * @param request The HttpRequest
     * @return template of lists management
     */
    public String getManage( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_INSERTAJAX_LIST );

        _strCurrentPageIndex = Paginator.getPageIndex( request, Paginator.PARAMETER_PAGE_INDEX, _strCurrentPageIndex );
        _nDefaultItemsPerPage = AppPropertiesService.getPropertyInt( PROPERTY_DEFAULT_INSERTAJAX_LIST_PER_PAGE, 50 );
        _nItemsPerPage = Paginator.getItemsPerPage( request, Paginator.PARAMETER_ITEMS_PER_PAGE, _nItemsPerPage,
                _nDefaultItemsPerPage );

        Collection<InsertAjax> listInsertAjaxList = InsertAjaxHome.findAll( getPlugin(  ) );
        listInsertAjaxList = AdminWorkgroupService.getAuthorizedCollection( listInsertAjaxList, getUser(  ) );

        Paginator paginator = new Paginator( (List<InsertAjax>) listInsertAjaxList, _nItemsPerPage,
                getHomeUrl( request ), PARAMETER_PAGE_INDEX, _strCurrentPageIndex );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_NB_ITEMS_PER_PAGE, "" + _nItemsPerPage );
        model.put( MARK_PAGINATOR, paginator );
        model.put( MARK_LIST_INSERTAJAX_LIST, paginator.getPageItems(  ) );

        HtmlTemplate templateList = AppTemplateService.getTemplate( TEMPLATE_MANAGE_INSERTAJAX, getLocale(  ), model );

        return getAdminPage( templateList.getHtml(  ) );
    }

    /**
      * Returns the form to create a insertajax
      *
      * @param request The Http request
      * @return the html code of the insertajax form
      */
    public String getCreate( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_CREATE );

        Map<String, Object> model = new HashMap<String, Object>(  );
        ReferenceList workgroupsList = AdminWorkgroupService.getUserWorkgroups( getUser(  ), getLocale(  ) );
        model.put( MARK_WORKGROUPS_LIST, workgroupsList );
        model.put( MARK_WEBAPP_URL, AppPathService.getBaseUrl( request ) );
        model.put( MARK_LOCALE, getLocale(  ) );

        HtmlTemplate defaultHtml = AppTemplateService.getTemplate( TEMPLATE_JAVASCRIPT );
        model.put( MARK_HTML_CONTENT, defaultHtml.getHtml(  ) );
        model.put( MARK_ROLES_LIST, RoleHome.getRolesList(  ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_INSERTAJAX, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Process the data capture form of a new insertajax
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     */
    public String doCreate( HttpServletRequest request )
    {
        String strName = request.getParameter( PARAMETER_INSERTAJAX_NAME );
        String strHtml = request.getParameter( PARAMETER_INSERTAJAX_HTML );
        String strSql = request.getParameter( PARAMETER_INSERTAJAX_SQL );
        String strWorkgroup = request.getParameter( PARAMETER_INSERTAJAX_WORKGROUP );
        String strRole = request.getParameter( PARAMETER_INSERTAJAX_ROLE );

        // Mandatory fields
        if ( ( strName == null ) || strName.trim(  ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        InsertAjax insertajax = new InsertAjax(  );

        insertajax.setName( strName );
        insertajax.setHtml( strHtml );
        insertajax.setSql( strSql );
        insertajax.setWorkgroup( strWorkgroup );
        insertajax.setRole( strRole );

        InsertAjaxHome.create( insertajax, getPlugin(  ) );

        // if the operation occurred well, redirects towards the list of the InsertAjaxs
        return JSP_REDIRECT_TO_MANAGE_INSERTAJAX;
    }

    /**
     * Process the data capture form of a new insertajax from copy of other
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     */
    public String doDuplicate( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_INSERTAJAX_ID ) );

        InsertAjax insertajax = InsertAjaxHome.findByPrimaryKey( nId, getPlugin(  ) );

        InsertAjax duplicateInsertAjax = new InsertAjax(  );
        duplicateInsertAjax.setName( insertajax.getName(  ) );
        duplicateInsertAjax.setHtml( insertajax.getHtml(  ) );
        duplicateInsertAjax.setSql( insertajax.getSql(  ) );
        duplicateInsertAjax.setWorkgroup( insertajax.getWorkgroup(  ) );
        duplicateInsertAjax.setRole( insertajax.getRole(  ) );

        InsertAjaxHome.create( duplicateInsertAjax, getPlugin(  ) );

        // if the operation occurred well, redirects towards the list of the InsertAjaxs
        return JSP_REDIRECT_TO_MANAGE_INSERTAJAX;
    }

    /**
     * Returns the form to update info about a insertPage
     *
     * @param request The Http request
     * @return The HTML form to update info
     */
    public String getModify( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_MODIFY );

        int nId = Integer.parseInt( request.getParameter( PARAMETER_INSERTAJAX_ID ) );
        InsertAjax insertPage = InsertAjaxHome.findByPrimaryKey( nId, getPlugin(  ) );

        Map<String, Object> model = new HashMap<String, Object>(  );
        ReferenceList workgroupsList = AdminWorkgroupService.getUserWorkgroups( getUser(  ), getLocale(  ) );
        model.put( MARK_WORKGROUPS_LIST, workgroupsList );
        model.put( MARK_WEBAPP_URL, AppPathService.getBaseUrl( request ) );
        model.put( MARK_LOCALE, getLocale(  ) );
        model.put( MARK_INSERTAJAX, insertPage );
        model.put( MARK_ROLES_LIST, RoleHome.getRolesList(  ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_INSERTAJAX, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Process the change form of a insertPage
     *
     * @param request The Http request
     * @return The Jsp URL of the process result
     */
    public String doModify( HttpServletRequest request )
    {
        // Mandatory fields
        if ( request.getParameter( PARAMETER_INSERTAJAX_NAME ).equals( "" ) ||
                request.getParameter( PARAMETER_INSERTAJAX_HTML ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        int nId = Integer.parseInt( request.getParameter( PARAMETER_INSERTAJAX_ID ) );

        InsertAjax insertPage = InsertAjaxHome.findByPrimaryKey( nId, getPlugin(  ) );
        insertPage.setName( request.getParameter( PARAMETER_INSERTAJAX_NAME ) );
        insertPage.setHtml( request.getParameter( PARAMETER_INSERTAJAX_HTML ) );
        insertPage.setSql( request.getParameter( PARAMETER_INSERTAJAX_SQL ) );
        insertPage.setWorkgroup( request.getParameter( PARAMETER_INSERTAJAX_WORKGROUP ) );
        insertPage.setRole( request.getParameter( PARAMETER_INSERTAJAX_ROLE ) );

        InsertAjaxHome.update( insertPage, getPlugin(  ) );

        // if the operation occurred well, redirects towards the list of the InsertAjaxs
        return JSP_REDIRECT_TO_MANAGE_INSERTAJAX;
    }

    /**
     * Manages the removal form of a insertPage whose identifier is in the http request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    public String getConfirmRemove( HttpServletRequest request )
    {
        int nIdInsertAjax = Integer.parseInt( request.getParameter( PARAMETER_INSERTAJAX_ID ) );

        UrlItem url = new UrlItem( JSP_DO_REMOVE_INSERTAJAX );
        url.addParameter( PARAMETER_INSERTAJAX_ID, nIdInsertAjax );
        url.addParameter( PARAMETER_ID_INSERTAJAX_LIST, request.getParameter( PARAMETER_ID_INSERTAJAX_LIST ) );

        Object[] args = { request.getParameter( PARAMETER_INSERTAJAX_NAME ) };

        return AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_INSERTAJAX, args, url.getUrl(  ),
            AdminMessage.TYPE_CONFIRMATION );
    }

    /**
     * Treats the removal form of a insertPage
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage insertPages
     */
    public String doRemove( HttpServletRequest request )
    {
        int nIdInsertAjax = Integer.parseInt( request.getParameter( PARAMETER_INSERTAJAX_ID ) );

        InsertAjax insertPage = InsertAjaxHome.findByPrimaryKey( nIdInsertAjax, getPlugin(  ) );
        InsertAjaxHome.remove( insertPage, getPlugin(  ) );

        // if the operation occurred well, redirects towards the list of the InsertAjaxs
        return JSP_REDIRECT_TO_MANAGE_INSERTAJAX;
    }
}
