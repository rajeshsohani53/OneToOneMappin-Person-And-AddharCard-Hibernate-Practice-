# OneToOneMapping02

A simple Java project that demonstrates a **bidirectional One-to-One mapping** in Hibernate ORM using a `Person` and their `Aadhar` card, with MySQL as the database.

## Tech Stack

- Java
- Hibernate ORM 6.3.1.Final (Jakarta Persistence)
- MySQL 8 (Connector/J 8.0.33)
- Maven (WAR packaging)

## Project Structure

```
src/main
├── java/com/rajesh
│   ├── entity
│   │   ├── Person.java          # Inverse side of the relationship
│   │   └── Aadhar.java          # Owning side (holds the foreign key)
│   ├── dbutil
│   │   └── PersonDao.java       # Persists a Person and their Aadhar
│   ├── utility
│   │   └── FactoryProvider.java # Provides the Hibernate SessionFactory
│   └── main
│       └── Main.java            # Entry point / demo
└── resources
    └── hibernate.cfg.xml        # Hibernate and database configuration
Notes/
└── hibernate_one_to_one_full_conclusion.md
```

## How the Mapping Works

- `Aadhar` is the **owning side**. It declares `@OneToOne` with `@JoinColumn(name = "forgin_key_person")`, so the `Aadhar` table holds the foreign key to `Person`.
- `Person` is the **inverse side**. It declares `@OneToOne(mappedBy = "person")` and does not create a column of its own.
- Both entities use `GenerationType.IDENTITY` for auto-incremented primary keys.

| Table    | Columns                                                                    |
|----------|----------------------------------------------------------------------------|
| `Person` | `id` (PK), `name`                                                          |
| `Aadhar` | `id` (PK), `aadharCardNumber`, `dateOfBirth`, `address`, `forgin_key_person` (FK) |

## Prerequisites

- JDK 11 or later
- Maven
- MySQL server running on `localhost:3306`

## Setup

1. Create the database:

   ```sql
   CREATE DATABASE OneToOneMapping02;
   ```

2. Update the connection settings in `src/main/resources/hibernate.cfg.xml` to match your MySQL setup:

   ```xml
   <property name="connection.url">jdbc:mysql://localhost:3306/OneToOneMapping02</property>
   <property name="connection.username">your_username</property>
   <property name="connection.password">your_password</property>
   ```

   Tables are created and updated automatically (`hbm2ddl.auto = update`), and SQL statements are logged to the console (`show_sql = true`).

3. Build the project:

   ```bash
   mvn clean install
   ```

## Running

Run `com.rajesh.main.Main` from your IDE, or from the command line:

```bash
mvn compile exec:java -Dexec.mainClass="com.rajesh.main.Main"
```

`Main` creates a `Person` and an `Aadhar`, links them in both directions, and saves both through `PersonDao.insert(...)`. Check the `Person` and `Aadhar` tables in MySQL to see the inserted rows.

## Notes

Extra notes on One-to-One mapping in Hibernate are in [`Notes/hibernate_one_to_one_full_conclusion.md`](Notes/hibernate_one_to_one_full_conclusion.md).

## Author

Rajesh Sohani
