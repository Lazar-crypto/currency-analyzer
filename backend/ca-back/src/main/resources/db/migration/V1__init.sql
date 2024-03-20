create table candle (id bigserial not null, ignore numeric(38,2), close_price numeric(38,2), close_time bigint, high_price numeric(38,2), low_price numeric(38,2), number_of_trades numeric(38,0), open_price numeric(38,2), open_time bigint, quote_asset_volume numeric(38,2), taker_buy_base_asset_volume numeric(38,2), taker_buy_quote_asset_volume numeric(38,2), volume numeric(38,2), currency_report_id bigint, primary key (id));
create table currency (id bigserial not null, name varchar(255), symbol varchar(255), primary key (id));
create table currency_report (id bigserial not null, mean_price_change numeric(38,2), standard_deviation numeric(38,2), currency_id bigint, report_id bigint, primary key (id));
create table report (id bigserial not null, creation_time timestamp(6), interval varchar(255) check (interval in ('ONE_MIN','THREE_MIN','FIVE_MIN','FIFTEEN_MIN','THIRTY_MIN','ONE_HOUR','TWO_HOUR','FOUR_HOUR','SIX_HOUR','EIGHT_HOUR','TWELVE_HOUR','ONE_DAY','THREE_DAY','ONE_WEEK','ONE_MONTH')), name varchar(255), primary key (id));
alter table if exists candle add constraint FKlpwdmt1cue63mcj2ipxed0k0d foreign key (currency_report_id) references currency_report;
alter table if exists currency_report add constraint FKpr0pnutdb2wjkupkud2lcpexr foreign key (currency_id) references currency;
alter table if exists currency_report add constraint FKslogge2syk572uibwg9ujt2un foreign key (report_id) references report;

INSERT INTO currency (name, symbol) VALUES
                                        ('Bitcoin', 'BTCUSDT'),
                                        ('Ethereum', 'ETHUSDT'),
                                        ('Binance Coin', 'BNBUSDT'),
                                        ('Cardano', 'ADAUSDT'),
                                        ('Ripple', 'XRPUSDT'),
                                        ('Solana', 'SOLUSDT'),
                                        ('Polkadot', 'DOTUSDT'),
                                        ('Dogecoin', 'DOGEUSDT'),
                                        ('Shiba Inu', 'SHIBUSDT'),
                                        ('Litecoin', 'LTCUSDT');


