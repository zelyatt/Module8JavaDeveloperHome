package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {
    private static final String DB_URL = "jdbc:h2:./test4";
    private static final String DB_USER = "sasa";
    private static final String DB_PASSWORD = "sasa";
    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;

    static {
        config.setJdbcUrl(DB_URL);
        config.setUsername(DB_USER);
        config.setPassword(DB_PASSWORD);
        ds = new HikariDataSource(config);
        initializeFlyway();
    }

    private static void initializeFlyway() {
        try {
            Flyway flyway = Flyway.configure()
                    .dataSource(ds)
                    .locations("db/migrations")
                    .load();
            flyway.migrate();
        } catch (Exception e) {
            System.err.println("Flyway migration failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static HikariDataSource getDataSource() {
        return ds;
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
