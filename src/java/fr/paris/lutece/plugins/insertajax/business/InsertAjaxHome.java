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

import fr.paris.lutece.portal.service.plugin.Plugin;

import java.util.Collection;


/**
 * This class provides instances management methods (create, find, ...) for InsertAjax objects
 */
public class InsertAjaxHome
{
    // Static variable pointed at the DAO instance
    private static IInsertAjaxDAO _dao = new InsertAjaxDAO(  );

    /**
     * Private constructor - this class need not be instantiated
     */
    private InsertAjaxHome(  )
    {
    }

    /**
      * Creation of an instance of InsertAjax
      *
      * @param InsertAjax The instance of the InsertAjax which contains the informations to store
      * @param plugin The Plugin object
      * @return The  instance of InsertAjax which has been created with its primary key.
      */
    public static InsertAjax create( InsertAjax insertAjax, Plugin plugin )
    {
        _dao.insert( insertAjax, plugin );

        return insertAjax;
    }

    /**
     * Update of the InsertAjax which is specified in parameter
     *
     * @param InsertAjax The instance of the InsertAjax which contains the data to store
     * @param plugin The Plugin object
     * @return The instance of the  InsertAjax which has been updated
     */
    public static InsertAjax update( InsertAjax insertAjax, Plugin plugin )
    {
        _dao.store( insertAjax, plugin );

        return insertAjax;
    }

    /**
     * Remove the InsertAjax whose identifier is specified in parameter
     * @param InsertAjax The InsertAjax object to remove
     * @param plugin The Plugin object
     */
    public static void remove( InsertAjax insertAjax, Plugin plugin )
    {
        _dao.delete( insertAjax, plugin );
    }

    ///////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Returns an instance of a InsertAjax whose identifier is specified in parameter
     * @param nKey The Primary key of the InsertAjax
     * @param plugin The Plugin object
     * @return An instance of InsertAjax
     */
    public static InsertAjax findByPrimaryKey( int nKey, Plugin plugin )
    {
        return _dao.load( nKey, plugin );
    }

    /**
     * Returns a collection of InsertAjax objects
     * @param plugin The Plugin object
     * @return A collection of InsertAjax
     */
    public static Collection<InsertAjax> findAll( Plugin plugin )
    {
        return _dao.selectAll( plugin );
    }

    /**
     * Returns a collection of InsertAjax objects with valid status
     * @param plugin The Plugin object
     * @return A collection of InsertAjax
     */
    public static Collection<InsertAjax> findEnabledInsertAjaxList( Plugin plugin )
    {
        return _dao.selectEnabledInsertAjaxList( plugin );
    }

    public static String executeSql( String strSql, Plugin plugin )
    {
        return _dao.executeSql( strSql, plugin );
    }

	public static Collection<Image> findImagesListOfWorkspace(int nIdWorksapce, Plugin _plugin)
	{
        return _dao.loadImagesListOfWorkspace( nIdWorksapce, _plugin );
	}
}
