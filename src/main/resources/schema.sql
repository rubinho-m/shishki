CREATE OR REPLACE FUNCTION get_bookings_by_account(
    p_account_id BIGINT
)
    RETURNS TABLE
            (
                id BIGINT,
                user_id    BIGINT,
                house_id   BIGINT,
                date_start DATE,
                date_end   DATE,
                unique_key INTEGER
            )
AS
'
    BEGIN
        RETURN QUERY
            SELECT b.id         AS id,
                   b.user_id    AS user_id,
                   b.house_id   AS house_id,
                   b.date_start AS date_start,
                   b.date_end   AS date_end,
                   b.unique_key AS unique_key
            FROM shishki_bookings b
                     JOIN shishki_accounts a ON b.user_id = a.id
            WHERE a.id = p_account_id;
    END;
' LANGUAGE plpgsql;