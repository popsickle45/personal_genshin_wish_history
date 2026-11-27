CREATE TABLE accounts (
                          id       INTEGER PRIMARY KEY AUTOINCREMENT,
                          uid      TEXT NOT NULL UNIQUE,
                          nickname TEXT
);

CREATE TABLE game_items (
                            id         INTEGER PRIMARY KEY AUTOINCREMENT,
                            name       TEXT NOT NULL UNIQUE,
                            item_type  TEXT NOT NULL CHECK (item_type IN ('CHARACTER', 'WEAPON')),
                            rarity     INTEGER NOT NULL CHECK (rarity IN (4, 5)),
                            pool       TEXT NOT NULL CHECK (pool IN ('STANDARD', 'LIMITED')),
                            is_builtin INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE banners (
                         id          INTEGER PRIMARY KEY AUTOINCREMENT,
                         name        TEXT NOT NULL,
                         banner_type TEXT NOT NULL CHECK (banner_type IN ('CHARACTER', 'WEAPON', 'STANDARD', 'CHRONICLED', 'BEGINNER')),
                         start_date  TEXT,
                         end_date    TEXT,
                         is_builtin  INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE banner_featured_items (
                                       banner_id    INTEGER NOT NULL REFERENCES banners(id) ON DELETE CASCADE,
                                       game_item_id INTEGER NOT NULL REFERENCES game_items(id),
                                       PRIMARY KEY (banner_id, game_item_id)
);

CREATE TABLE wishes (
                        id           INTEGER PRIMARY KEY AUTOINCREMENT,
                        account_id   INTEGER NOT NULL REFERENCES accounts(id),
                        banner_id    INTEGER NOT NULL REFERENCES banners(id),
                        game_item_id INTEGER REFERENCES game_items(id),
                        wished_at    TEXT NOT NULL
);

CREATE INDEX idx_wishes_account_banner ON wishes (account_id, banner_id, wished_at, id);