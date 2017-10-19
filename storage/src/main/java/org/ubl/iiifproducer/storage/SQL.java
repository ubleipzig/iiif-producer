/*
 * IIIFProducer
 *
 * Copyright (C) 2017 Leipzig University Library <info@ub.uni-leipzig.de>
 *
 * @author Stefan Freitag <freitag@uni-leipzig.de>
 * @author Christopher Johnson <christopher_hanna.johnson@uni-leipzig.de>
 * @author Felix Kreißig <kreissig@ub.uni-leipzig.de>
 * @author Leander Seige <seige@ub.uni-leipzig.de>
 * @license http://opensource.org/licenses/gpl-2.0.php GNU GPLv2
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.ubl.iiifproducer.storage;

import static java.lang.Integer.parseInt;
import static java.sql.DriverManager.getConnection;
import static org.slf4j.LoggerFactory.getLogger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;

public class SQL {

    //TODO test this
    private static Logger logger = getLogger(SQL.class);
    private String title;
    private String viewId;
    private String jdbcURI;

    public SQL(String title, String viewId) {
        this.title = title;
        this.viewId = viewId;
        this.jdbcURI = jdbcURI;
    }

    public void initDb() {
        updateSQLdig(title, Integer.toString(parseInt(viewId)));
    }

    public void updateSQLdig(String title, String viewId) {
        String sqlSelect = "select view_id from metadata where title like '" + title + "'";
        String sqlUpdate =
                "update metadata set view_id = '" + viewId + "' where title like '" + title + "'";
        try (Connection conn = this.connect();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(sqlSelect);) {
            while (rs.next()) {
                String vid = rs.getString(1);
                if (vid.isEmpty()) {
                    statement.executeUpdate(sqlUpdate);
                }
            }
            logger.info("Update DigiLife-Datenbank on " + title + " with the ViewID " + Integer
                    .toString(parseInt(viewId)));
            statement.close();
            conn.close();
        } catch (SQLException e) {
            logger.error("Datenbankeintrag der ViewID " + viewId + " hat nicht geklappt.");
            logger.error("SQL Exception", e.fillInStackTrace());
        }
    }

    private Connection connect() {
        String url = jdbcURI;
        Connection conn = null;
        try {
            conn = getConnection(url);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return conn;
    }
}
