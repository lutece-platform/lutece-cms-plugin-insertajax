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
package fr.paris.lutece.plugins.insertajax.business;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;


/**
 * This class provides Data Access methods for InsertAjax objects
 */
public class InsertAjaxDAO implements IInsertAjaxDAO
{
    // Constants
    private static final String SQL_QUERY_NEWPK = "SELECT max( id_insertajax_request ) FROM insertajax_request ";
    private static final String SQL_QUERY_SELECT = "SELECT id_insertajax_request, name, html, sqlrequest, workgroup_key, role FROM insertajax_request WHERE id_insertajax_request = ? ";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_insertajax_request, name, sqlrequest, workgroup_key, role FROM insertajax_request";
    private static final String SQL_QUERY_SELECT_ENABLED_insertAjax_LIST = "SELECT id_insertajax_request, name, html, sqlrequest, workgroup_key FROM insertajax_request";
    private static final String SQL_QUERY_INSERT = "INSERT INTO insertajax_request ( id_insertajax_request , name, html, sqlrequest, workgroup_key, role, date_update )  VALUES ( ?, ?, ?, ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM insertajax_request WHERE id_insertajax_request = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE insertajax_request SET name = ? , html = ?, sqlrequest = ?, workgroup_key = ?, role = ?, date_update = ? WHERE id_insertajax_request = ?  ";
    private static final String SQL_LIST_IMG = "SELECT id_document, summary FROM document WHERE id_space = ?";
    
    /**
    * Generates a new primary key
    * @param plugin The plugin
    * @return The new primary key
    */
    private int newPrimaryKey( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEWPK, plugin );
        daoUtil.executeQuery(  );

        int nKey;

        if ( !daoUtil.next(  ) )
        {
            // if the table is empty
            nKey = 1;
        }

        nKey = daoUtil.getInt( 1 ) + 1;

        daoUtil.free(  );

        return nKey;
    }

    ////////////////////////////////////////////////////////////////////////
    // Methods using a dynamic pool

    /**
     * Insert a new record in the table.
     *
     * @param insertAjax The insertAjax object
     * @param plugin The plugin
     */
    public void insert( InsertAjax insertAjax, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );
        int id = newPrimaryKey( plugin );
        insertAjax.replacePrimaryKey(id);
        insertAjax.setId( id );
        daoUtil.setInt( 1, insertAjax.getId(  ) );
        daoUtil.setString( 2, insertAjax.getName(  ) );
        daoUtil.setString( 3, insertAjax.getHtml(  ) );
        daoUtil.setString( 4, insertAjax.getSql(  ) );
        daoUtil.setString( 5, insertAjax.getWorkgroup(  ) );
        daoUtil.setString( 6, insertAjax.getRole(  ) );
        daoUtil.setTimestamp( 7, new Timestamp( new java.util.Date(  ).getTime(  ) ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load the data of insertAjax from the table
     * @param ninsertAjaxId The identifier of insertAjax
     * @param plugin The plugin
     * @return the instance of the insertAjax
     */
    public InsertAjax load( int nInsertAjaxId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nInsertAjaxId );
        daoUtil.executeQuery(  );

        InsertAjax insertAjax = null;

        if ( daoUtil.next(  ) )
        {
            insertAjax = new InsertAjax(  );
            insertAjax.setId( daoUtil.getInt( 1 ) );
            insertAjax.setName( daoUtil.getString( 2 ) );
            insertAjax.setHtml( daoUtil.getString( 3 ) );
            insertAjax.setSql( daoUtil.getString( 4 ) );
            insertAjax.setWorkgroup( daoUtil.getString( 5 ) );
            insertAjax.setRole( daoUtil.getString( 6 ) );
        }

        daoUtil.free(  );

        return insertAjax;
    }

    /**
     * Delete a record from the table
     * @param insertAjax The insertAjax object
     * @param plugin The plugin
     */
    public void delete( InsertAjax insertAjax, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, insertAjax.getId(  ) );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Update the record in the table
     * @param insertAjax The reference of insertAjax
     * @param plugin The plugin
     */
    public void store( InsertAjax insertAjax, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        int ninsertAjaxId = insertAjax.getId(  );

        daoUtil.setString( 1, insertAjax.getName(  ) );
        daoUtil.setString( 2, insertAjax.getHtml(  ) );
        daoUtil.setString( 3, insertAjax.getSql(  ) );
        daoUtil.setString( 4, insertAjax.getWorkgroup(  ) );
        daoUtil.setString( 5, insertAjax.getRole(  ) );
        daoUtil.setTimestamp( 6, new Timestamp( new java.util.Date(  ).getTime(  ) ) );
        daoUtil.setInt( 7, ninsertAjaxId );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load the list of insertAjaxs
     *
     * @param plugin The plugin
     * @return The Collection of the insertAjaxs
     */
    public Collection<InsertAjax> selectAll( Plugin plugin )
    {
        Collection<InsertAjax> insertAjaxList = new ArrayList<InsertAjax>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            InsertAjax insertAjax = new InsertAjax(  );
            insertAjax.setId( daoUtil.getInt( 1 ) );
            insertAjax.setName( daoUtil.getString( 2 ) );
            //we don't bring the html code here. Just the sql request.
            insertAjax.setSql( daoUtil.getString( 3 ) );
            insertAjax.setWorkgroup( daoUtil.getString( 4 ) );
            insertAjax.setRole( daoUtil.getString( 5 ) );
            insertAjaxList.add( insertAjax );
        }

        daoUtil.free(  );

        return insertAjaxList;
    }

    /**
     * Load the list of insertAjaxs
     *
     * @param plugin The plugin
     * @return The Collection of the insertAjaxs
     */
    public Collection<InsertAjax> selectEnabledInsertAjaxList( Plugin plugin )
    {
        Collection<InsertAjax> insertAjaxList = new ArrayList<InsertAjax>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_ENABLED_insertAjax_LIST, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            InsertAjax insertAjax = new InsertAjax(  );
            insertAjax.setId( daoUtil.getInt( 1 ) );
            insertAjax.setName( daoUtil.getString( 2 ) );
            insertAjax.setHtml( daoUtil.getString( 3 ) );
            insertAjax.setSql( daoUtil.getString( 4 ) );
            insertAjax.setWorkgroup( daoUtil.getString( 5 ) );
            insertAjaxList.add( insertAjax );
        }

        daoUtil.free(  );

        return insertAjaxList;
    }

    /*
     * @see fr.paris.lutece.plugins.insertajax.business.IInsertAjaxDAO#executeSql(java.lang.String, fr.paris.lutece.portal.service.plugin.Plugin)
     */
    public String executeSql( String strSql, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( strSql, plugin );
        daoUtil.executeQuery(  );

        String strResult = "";

        while ( daoUtil.next(  ) )
        {
            strResult = daoUtil.getString( 1 );
        }

        daoUtil.free(  );

        return strResult;
    }

	public Collection<Image> loadImagesListOfWorkspace(int nIdWorkspace, Plugin _plugin)
	{
		
		//TODO check that document plugin is installed
		//TODO check the role and workgroup to access this workspace
		//TODO retreive images from this workspace
		
        Collection<Image> imagesList = new ArrayList<Image>(  );
		
        DAOUtil daoUtil = new DAOUtil( SQL_LIST_IMG, _plugin );
        daoUtil.setInt( 1, nIdWorkspace );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
        	Image img = new Image();
        	img.setId( daoUtil.getInt( 1 ) );
        	img.setComment( daoUtil.getString( 2 ) );
        	imagesList.add( img );
        }

        daoUtil.free(  );
        return imagesList;
	}
}
