package de.lathspell.java_test_ee7_jpa.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.annotations.Array;
import org.eclipse.persistence.annotations.Struct;

@Entity
@Table(name = "array_example", indexes = @Index(name = "param_idx", columnList = ("params")))
@Struct(name = "params") // Else: [EclipseLink-157] "Normal descriptors do not support non-relational extensions."
@XmlRootElement
public class ArrayExamplePostgres implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;

    /*
    "TEXT"
    2014-05-12 23:03:08 CEST postgres@java_test_ee7_jpa [4183] LOG:  execute <unnamed>: SELECT pg_type.oid   FROM pg_catalog.pg_type   LEFT   JOIN (select ns.oid as nspoid, ns.nspname, r.r           from pg_namespace as ns           join ( select s.r, (current_schemas(false))[s.r] as nspname                    from generate_series(1, array_upper(current_schemas(false), 1)) as s(r) ) as r          using ( nspname )        ) as sp     ON sp.nspoid = typnamespace  WHERE typname = $1  ORDER BY sp.r, pg_type.oid DESC LIMIT 1
    2014-05-12 23:03:08 CEST postgres@java_test_ee7_jpa [4183] DETAIL:  parameters: $1 = '_TEXT'
    => 0 rows


    "text"
    2014-05-12 23:04:46 CEST postgres@java_test_ee7_jpa [4266] LOG:  execute <unnamed>: SELECT pg_type.oid   FROM pg_catalog.pg_type   LEFT   JOIN (select ns.oid as nspoid, ns.nspname, r.r           from pg_namespace as ns           join ( select s.r, (current_schemas(false))[s.r] as nspname                    from generate_series(1, array_upper(current_schemas(false), 1)) as s(r) ) as r          using ( nspname )        ) as sp     ON sp.nspoid = typnamespace  WHERE typname = $1  ORDER BY sp.r, pg_type.oid DESC LIMIT 1
    2014-05-12 23:04:46 CEST postgres@java_test_ee7_jpa [4266] DETAIL:  parameters: $1 = '_text'
    --> 1009
    2014-05-12 23:04:46 CEST postgres@java_test_ee7_jpa [4266] LOG:  execute <unnamed>: SELECT e.typdelim FROM pg_catalog.pg_type t, pg_catalog.pg_type e WHERE t.oid = $1 and t.typelem = e.oid
    2014-05-12 23:04:46 CEST postgres@java_test_ee7_jpa [4266] DETAIL:  parameters: $1 = '1009'
    --> ','
    => The column is created as "character varying(255)"

    The following INSERT would be correct though:
    2014-05-12 23:08:57 CEST postgres@java_test_ee7_jpa [4352] LOG:  execute <unnamed>: INSERT INTO array_example (params) VALUES ($1)
    2014-05-12 23:08:57 CEST postgres@java_test_ee7_jpa [4352] DETAIL:  parameters: $1 = '{one,two,three}'


    "text[]"
    2014-05-12 23:12:21 CEST postgres@java_test_ee7_jpa [4435] LOG:  execute <unnamed>: SELECT pg_type.oid   FROM pg_catalog.pg_type   LEFT   JOIN (select ns.oid as nspoid, ns.nspname, r.r           from pg_namespace as ns           join ( select s.r, (current_schemas(false))[s.r] as nspname                    from generate_series(1, array_upper(current_schemas(false), 1)) as s(r) ) as r          using ( nspname )        ) as sp     ON sp.nspoid = typnamespace  WHERE typname = $1  ORDER BY sp.r, pg_type.oid DESC LIMIT 1
    2014-05-12 23:12:21 CEST postgres@java_test_ee7_jpa [4435] DETAIL:  parameters: $1 = '_text[]'
    -> 0 rows
    */
    @NotNull
    @Array(databaseType = "text" /*, targetClass = String.class */) // Needs @Struct on class!
    @Column(name = "params")
    private List<String> params;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

}
