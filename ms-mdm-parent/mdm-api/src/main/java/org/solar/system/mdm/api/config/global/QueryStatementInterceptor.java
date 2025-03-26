package org.solar.system.mdm.api.config.global;

import org.hibernate.resource.jdbc.spi.StatementInspector;

public class QueryStatementInterceptor implements StatementInspector {

    @Override
    public String inspect(final String sql) {
        QueryContextHolder.setQuery(sql);
        return sql;
    }

}
