/*
 * ============================================================================
 * Project betoffice-storage Copyright (c) 2000-2019 by Andre Winkler. All
 * rights reserved.
 * ============================================================================
 * GNU GENERAL PUBLIC LICENSE TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND
 * MODIFICATION
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */

package de.betoffice.wrapper;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import de.betoffice.database.data.DeleteDatabase;
import de.betoffice.database.data.MySqlDatabasedTestSupport;
import de.betoffice.database.data.MySqlDatabasedTestSupport.DataLoader;
import de.winkler.betoffice.storage.UserResult;

/**
 * Database test setup and tear down methods.
 * 
 * @author by Andre Winkler
 */
public final class DatabaseSetUpAndTearDown {

    private final DataSource dataSource;

    private MySqlDatabasedTestSupport mysql;

    public DatabaseSetUpAndTearDown(final DataSource _dataSource) {
        dataSource = _dataSource;
    }

    /**
     * Here we delete and set up the test database. This is an expensive
     * operation!.
     * 
     * @param _dataLoader
     *            The data for the setup
     * @throws Exception
     *             Problems?
     */
    public void setUp(final DataLoader _dataLoader) throws Exception {
        Connection conn = getConnection();
        mysql = new MySqlDatabasedTestSupport();
        try {
            UserResult.nEqualValue = 13;
            UserResult.nTotoValue = 10;
            UserResult.nZeroValue = 0;

            // Delete and setup of the test database!
            mysql = new MySqlDatabasedTestSupport();
            mysql.setUp(conn, _dataLoader);
        } finally {
            conn.close();
        }
    }

    public void tearDown() throws SQLException {
        Connection conn = getConnection();
        try {
            DeleteDatabase.deleteDatabase(conn);
        } finally {
            conn.close();
        }
    }

    private Connection getConnection() throws SQLException {
        Connection conn = dataSource.getConnection();
        conn.setAutoCommit(false);
        return conn;
    }

}
