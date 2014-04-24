/*
 * Copyright (c) 2002-2014, Mairie de Paris
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

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.insertajax.business.InsertAjax;
import fr.paris.lutece.plugins.insertajax.business.InsertAjaxHome;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;

/**
 * Return content to the insertAjax Javascript client define in the adminFeature
 */
public class InsertAjaxResult
{
    private final static String PARAMETER_SQL_REQUEST_ID = "id";
    private final static String PARAMETER_PLUGIN_NAME = "plugin_name";
    private final static String DEFAULT_RESULT = "-1";

    public static String getResult( HttpServletRequest request )
    {
        try
		{
        	//get plugin
			String strPluginName = request.getParameter( PARAMETER_PLUGIN_NAME );
			Plugin plugin = PluginService.getPlugin( strPluginName );
			
			String strIdSqlRequest = request.getParameter( PARAMETER_SQL_REQUEST_ID );
			int nIdSqlRequest = Integer.parseInt( strIdSqlRequest );
			
			//get the insertajax
			InsertAjax ia = InsertAjaxHome.findByPrimaryKey( nIdSqlRequest, plugin );
			String strSql = ia.getSql(  );
			
			//get result
			String strResult = InsertAjaxHome.executeSql( strSql, plugin );
			
			//JSONObject json = new JSONObject();
			
			return strResult;
		}
        catch (NumberFormatException e)
		{
			return DEFAULT_RESULT;
		}
        catch (Exception e)
		{
			return DEFAULT_RESULT;
		}
    }
}