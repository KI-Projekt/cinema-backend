# Cinema Backend (WWI21SEB Gruppe 4)

 [![codecov](https://codecov.io/gh/mabu2807/cinema-backend/branch/main/graph/badge.svg)](https://codecov.io/gh/mabu2807/cinema-backend)

## Bi-direktionale vs Uni-direktionale Datenbankbeziehungen

Als Notiz möchte ich hier vermerken, was der Unterschied zwischen eier **Bi-direktionalen** Datenbankbeziehung 
und einer **Uni-direktionalen** Beziehung ist und wann welche verwendet werden soll.


### Bi-direktional
Eine bi-direktionale Beziehung wird dann benutzt, wenn Parent- und Child-Tabellen,
beide von ihrer Beziehung zueinander mitbekommen sollen. 
Heißt: Parent-Tabelle hat eine Liste von Child als Attribut, z.B. List<Screenings> (welche nicht in der eigentlichen
Tabelle gespeichert wird, sondern nur der API dienen soll).

Diese Beziehung wird mithilfe der Annotations **@ManyToOne** und **@OneToMany** in Hibernate implementiert.

### Uni-direktional
Lässt man eines der beiden Annotations in der Beziehung weg (z.B. man hat nur @ManyToOne), so entsteht eine uni-direktionale
Datenbankbeziehung. Dadurch weiß nur die Child-Tabelle von ihrer Beziehung zu dem Parent.

### Vergleich
Konkret gesagt, ist es *Best-Practice* bei einer **kleinen** Anzahl an zu erwartenden Entitäten, 
eine bi-direktionale Datenbankbeziehung zu verwenden.
Nachteil beim bi-direktionalen Ansatz ist, dass die API-Ergebnisse *weder "gepaged" noch gefiltert werden* können 
und **alle** Entitäten bei der Abfrage von Parent-Entitäten ausgegeben werden.

Entsprechend eignen sich uni-direktionale Beziehung für **größere** Datensätze (wie bei "ein Film kann **(sehr) viele** Vorstellungen haben").
Nachteil beim Uni-direktionalen Ansatz ist, dass ihre Logik mehr geschriebenen Code benötigt und angeblich mehr Performance kosten soll.

Weitere Beiträge:
- https://stackoverflow.com/a/47711251
- https://stackoverflow.com/a/49806745
- https://dzone.com/articles/deterring-%E2%80%9Ctomany%E2%80%9D



# MYSQL RESERVED KEYWORDS
**Die aufgelisteten keywords nicht als Variablennamen benutzen!**

A

    ACTIVE

    ADMIN

    ARRAY

    ATTRIBUTE

    AUTHENTICATION

B

    BUCKETS

    BULK

C

    CHALLENGE_RESPONSE

    CLONE

    COMPONENT

    CUME_DIST (R)

D

    DEFINITION

    DENSE_RANK (R)

    DESCRIPTION

E

    EMPTY (R)

    ENFORCED

    ENGINE_ATTRIBUTE

    EXCEPT (R)

    EXCLUDE

F

    FACTOR

    FAILED_LOGIN_ATTEMPTS

    FINISH

    FIRST_VALUE (R)

    FOLLOWING

G

    GENERATE

    GEOMCOLLECTION

    GET_MASTER_PUBLIC_KEY

    GET_SOURCE_PUBLIC_KEY

    GROUPING (R)

    GROUPS (R)

    GTID_ONLY

H

    HISTOGRAM

    HISTORY

I

    INACTIVE

    INITIAL

    INITIATE

    INTERSECT (R)

    INVISIBLE

J

    JSON_TABLE (R)

    JSON_VALUE

K

    KEYRING

L

    LAG (R)

    LAST_VALUE (R)

    LATERAL (R)

    LEAD (R)

    LOCKED

M

    MASTER_COMPRESSION_ALGORITHMS

    MASTER_PUBLIC_KEY_PATH

    MASTER_TLS_CIPHERSUITES

    MASTER_ZSTD_COMPRESSION_LEVEL

    MEMBER

N

    NESTED

    NETWORK_NAMESPACE

    NOWAIT

    NTH_VALUE (R)

    NTILE (R)

    NULLS

O

    OF (R)

    OFF

    OJ

    OLD

    OPTIONAL

    ORDINALITY

    ORGANIZATION

    OTHERS

    OVER (R)

P

    PASSWORD_LOCK_TIME

    PATH

    PERCENT_RANK (R)

    PERSIST

    PERSIST_ONLY

    PRECEDING

    PRIVILEGE_CHECKS_USER

    PROCESS

R

    RANDOM

    RANK (R)

    RECURSIVE (R)

    REFERENCE

    REGISTRATION

    REPLICA

    REPLICAS

    REQUIRE_ROW_FORMAT

    RESOURCE

    RESPECT

    RESTART

    RETAIN

    RETURNING

    REUSE

    ROLE

    ROW_NUMBER (R)

S

    SECONDARY

    SECONDARY_ENGINE

    SECONDARY_ENGINE_ATTRIBUTE

    SECONDARY_LOAD

    SECONDARY_UNLOAD

    SKIP

    SOURCE_AUTO_POSITION

    SOURCE_BIND

    SOURCE_COMPRESSION_ALGORITHMS

    SOURCE_CONNECT_RETRY

    SOURCE_DELAY

    SOURCE_HEARTBEAT_PERIOD

    SOURCE_HOST

    SOURCE_LOG_FILE

    SOURCE_LOG_POS

    SOURCE_PASSWORD

    SOURCE_PORT

    SOURCE_PUBLIC_KEY_PATH

    SOURCE_RETRY_COUNT

    SOURCE_SSL

    SOURCE_SSL_CA

    SOURCE_SSL_CAPATH

    SOURCE_SSL_CERT

    SOURCE_SSL_CIPHER

    SOURCE_SSL_CRL

    SOURCE_SSL_CRLPATH

    SOURCE_SSL_KEY

    SOURCE_SSL_VERIFY_SERVER_CERT

    SOURCE_TLS_CIPHERSUITES

    SOURCE_TLS_VERSION

    SOURCE_USER

    SOURCE_ZSTD_COMPRESSION_LEVEL

    SRID

    STREAM

    SYSTEM (R)

T

    THREAD_PRIORITY

    TIES

    TLS

U

    UNBOUNDED

    UNREGISTER

    URL

V

    VCPU

    VISIBLE

W

    WINDOW (R)

Z

    ZONE

