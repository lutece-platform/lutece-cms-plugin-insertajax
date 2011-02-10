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
 * InsertAjaxInterface
 */
public interface IInsertAjaxDAO
{
    /**
     * Delete a record from the table
     * @param InsertAjax The InsertAjax object
     * @param plugin The plugin
     */
    void delete( InsertAjax insertAjax, Plugin plugin );

    /**
     * Insert a new record in the table.
     * @param InsertAjax The InsertAjax object
     * @param plugin The plugin
     */
    void insert( InsertAjax insertAjax, Plugin plugin );

    /**
     * Load the data of InsertAjax from the table
     * @param nInsertAjaxId The identifier of InsertAjax
     * @param plugin The plugin
     * @return the instance of the InsertAjax
     */
    InsertAjax load( int nInsertAjaxId, Plugin plugin );

    /**
     * Load the list of InsertAjaxs
     * @param plugin The plugin
     * @return The Collection of the InsertAjaxs
     */
    Collection<InsertAjax> selectAll( Plugin plugin );

    /**
     * Load the list of InsertAjaxs with valid status
     * @param plugin The plugin
     * @return The Collection of the InsertAjaxs
     */
    Collection<InsertAjax> selectEnabledInsertAjaxList( Plugin plugin );

    /**
     * Update the record in the table
     * @param InsertAjax The reference of InsertAjax
     * @param plugin The plugin
     */
    void store( InsertAjax insertAjax, Plugin plugin );

    /**
     * Excute the sql query for the ajax call
     * @param strSql
     * @param plugin
     * @return
     */
    String executeSql( String strSql, Plugin plugin );

    Collection<Image> loadImagesListOfWorkspace(int nIdWorksapce, Plugin _plugin);
}
