/*
 * Copyright (c) 2002-2013, Mairie de Paris
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
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.service.admin.AdminUserService;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.workgroup.AdminWorkgroupService;
import fr.paris.lutece.portal.web.constants.Messages;
import fr.paris.lutece.portal.web.insert.InsertServiceJspBean;
import fr.paris.lutece.portal.web.insert.InsertServiceSelectionBean;
import fr.paris.lutece.util.html.HtmlTemplate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * InsertService main class
 */
public class InsertAjaxInsertServiceJspBean extends InsertServiceJspBean implements InsertServiceSelectionBean
{
    ////////////////////////////////////////////////////////////////////////////
    // Constants
    private static final String TEMPLATE_SELECTOR_PAGE = "admin/plugins/insertajax/insertajax_selector.html";
    private static final String PARAMETER_ID = "id";
    private static final String PARAMETER_INPUT = "input";
    private static final String INSERT_AJAX_LIST = "insertajax_list";
    private static final String PARAMETER_PLUGIN_NAME = "plugin_name";
    private AdminUser _user;
    private Plugin _plugin;
    private String _input;

    ////////////////////////////////////////////////////////////////////////////
    // Methods
    private void init( HttpServletRequest request )
    {
        String strPluginName = request.getParameter( PARAMETER_PLUGIN_NAME );
        _user = AdminUserService.getAdminUser( request );
        _plugin = PluginService.getPlugin( strPluginName );
        _input = request.getParameter( PARAMETER_INPUT );
    }

    /**
     *
     * @param request The Http Request
     * @return The html form.
     */
    @SuppressWarnings( "unchecked" )
    public String getInsertServiceSelectorUI( HttpServletRequest request )
    {
        init( request );

        Collection<InsertAjax> insertAjaxList = InsertAjaxHome.findAll( _plugin );
        insertAjaxList = AdminWorkgroupService.getAuthorizedCollection( insertAjaxList, _user );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( INSERT_AJAX_LIST, insertAjaxList );
        model.put( PARAMETER_INPUT, _input );

        Locale locale = AdminUserService.getLocale( request );
        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_SELECTOR_PAGE, locale, model );

        return template.getHtml(  );
    }

    public String doInsertLink( HttpServletRequest request )
    {
        String strIdInsertAjax = request.getParameter( PARAMETER_ID );
        int nIdInsertAjax = Integer.parseInt( strIdInsertAjax );
        String strInput = request.getParameter( PARAMETER_INPUT );

        // Check mandatory fields
        if ( !( nIdInsertAjax > 0 ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        InsertAjax insertAjax = InsertAjaxHome.findByPrimaryKey( nIdInsertAjax, _plugin );

        return insertUrlWithoutEscape( request, strInput, insertAjax.getHtml(  ) );
    }
}
