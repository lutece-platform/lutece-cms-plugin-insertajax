/*
 * Copyright (c) 2002-2009, Mairie de Paris
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
 *  3. Neither the description of 'Mairie de Paris' nor 'Lutece' nor the descriptions of its
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
package fr.paris.lutece.plugins.insertajax.business;

import fr.paris.lutece.plugins.insertajax.service.InsertAjaxWorkgroupRemovalListener;
import fr.paris.lutece.portal.service.workgroup.AdminWorkgroupResource;
import fr.paris.lutece.portal.service.workgroup.AdminWorkgroupService;
import fr.paris.lutece.portal.service.workgroup.WorkgroupRemovalListenerService;


/**
 * This class represents business object InsertAjax
 */
public class InsertAjax implements AdminWorkgroupResource
{
    /////////////////////////////////////////////////////////////////////////////////
    // Constants
    public static final String RESOURCE_TYPE = "INSERTAJAX";
    public static final String ROLE_NONE = "none";
	private static final String ID_PRIMARY_KEY = "ID_PRIMARY_KEY";
    private static InsertAjaxWorkgroupRemovalListener _listenerWorkgroup;
    private int _nId;
    private String _strName;
    private String _strHtml;
    private String _strSql;
    private String _strWorkgroupKey;
    private String _strAdminWorkgroup;
    private String _strRole;

    /** Creates a new instance of InsertAjax */
    public InsertAjax(  )
    {
    }

    /**
    * Initialize the InsertAjaxList
    */
    public static void init(  )
    {
        // Create removal listeners and register them
        if ( _listenerWorkgroup == null )
        {
            _listenerWorkgroup = new InsertAjaxWorkgroupRemovalListener(  );
            WorkgroupRemovalListenerService.getService(  ).registerListener( _listenerWorkgroup );
        }
    }

    /**
     * getters and setters
     */
    public int getId(  )
    {
        return _nId;
    }

    public void setId( int nId )
    {
        _nId = nId;
    }

    public String getName(  )
    {
        return _strName;
    }

    public void setName( String name )
    {
        _strName = name;
    }

    public String getHtml(  )
    {
        return _strHtml;
    }

    public void setHtml( String html )
    {
        _strHtml = html;
    }

    public String getSql(  )
    {
        return _strSql;
    }

    public void setSql( String sql )
    {
        _strSql = sql;
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    // Workgtoup management

    /**
     * Return the WorkgroupKey Contact Label
     * @return The label of the selected Workgroup Key
     */
    public String getWorkgroupKey(  )
    {
        return _strWorkgroupKey;
    }

    /**
     * Sets the description of the Contact with the specified String
     * @param strWorkgroupKey The workgroup key
     */
    public void setWorkgroupKey( String strWorkgroupKey )
    {
        _strWorkgroupKey = strWorkgroupKey;
    }

    /**
     * Returns the workgroup
     * @return The workgroup
     */
    public String getWorkgroup(  )
    {
        return _strAdminWorkgroup;
    }

    /**
     * Sets the workgroup
     * @param strAdminWorkgroup The workgroup
     */
    public void setWorkgroup( String strAdminWorkgroup )
    {
        _strAdminWorkgroup = AdminWorkgroupService.normalizeWorkgroupKey( strAdminWorkgroup );
    }

    public String getRole(  )
    {
        return _strRole;
    }

    public void setRole( String role )
    {
        _strRole = role;
    }

    /**
     * The javascript generated for a linkservice can use the DB id from the insertajax
     * But we don't know it in advance. This method subtitute the ID_PRIMARY_KEY string 
     * with the one given by the database.
     * @param id
     */
	public void replacePrimaryKey(int id)
	{
		String html = getHtml();
		html = html.replaceAll(ID_PRIMARY_KEY, id+"");
		setHtml(html);
	}
}
