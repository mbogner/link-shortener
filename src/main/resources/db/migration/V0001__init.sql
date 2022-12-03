-- we are using uuids as default id values
create extension if not exists "uuid-ossp";

-- let's use utc for sure
DROP FUNCTION IF EXISTS now_utc;
create or replace function now_utc() returns timestamp as
$$
select now() at time zone 'utc';
$$ language sql;

-- enforce that created at can't be changed
DROP FUNCTION IF EXISTS trg_func__check_created_at_unchanged;
CREATE OR REPLACE FUNCTION trg_func__check_created_at_unchanged()
    RETURNS TRIGGER AS
$$
BEGIN
    IF OLD.created_at != NEW.created_at THEN
        RAISE EXCEPTION 'attempted to update created_at' USING HINT = 'updated_at field can not be changed';
    END IF;
    RETURN NEW;
END;
$$ language plpgsql;

-- set updated_at to now if not set for an update
DROP FUNCTION IF EXISTS trg_func__update_updated_at;
CREATE OR REPLACE FUNCTION trg_func__update_updated_at()
    RETURNS TRIGGER AS
$$
BEGIN
    IF NEW.updated_at IS NULL OR NEW.updated_at = OLD.updated_at THEN
        NEW.updated_at = now_utc();
    END IF;
    IF NEW.updated_at < OLD.updated_at THEN
        RAISE EXCEPTION 'attempted to go back in history with updated_at' USING HINT = 'updated_at can only increment';
    END IF;
    RETURN NEW;
END;
$$ language plpgsql;

-- increment lock_version if not done
DROP FUNCTION IF EXISTS trg_func__increment_lock_version;
CREATE OR REPLACE FUNCTION trg_func__increment_lock_version()
    RETURNS TRIGGER AS
$$
BEGIN
    IF NEW.lock_version = OLD.lock_version THEN
        NEW.lock_version = OLD.lock_version + 1;
    END IF;
    RETURN NEW;
END;
$$ language plpgsql;

-- increment lock_version if not done
DROP FUNCTION IF EXISTS unknown_enum_value;
CREATE OR REPLACE FUNCTION unknown_enum_value()
    RETURNS varchar AS
$$
BEGIN
    RETURN 'UNKNOWN';
END;
$$ language plpgsql;
