package generated;

import java.lang.String;

public class PlayConf {
  public static String application_mode() {
    switch(play.Play.id){
      case "test": return "dev";
      case "prod": return "prod";
     }
    return "dev";
  }

  public static String application_name() {
    switch(play.Play.id){
      case "test": return "play1-conf-accessor-test";
      case "prod": return "play1-conf-accessor-test";
     }
    return "play1-conf-accessor-test";
  }

  public static String application_secret() {
    switch(play.Play.id){
      case "test": return "pxMajZUysW2eqEJuAvPnotjR705j2LAquhYwRgunTTJfPN5OXWhSRDQmQMTSLrUt";
      case "prod": return "pxMajZUysW2eqEJuAvPnotjR705j2LAquhYwRgunTTJfPN5OXWhSRDQmQMTSLrUt";
     }
    return "pxMajZUysW2eqEJuAvPnotjR705j2LAquhYwRgunTTJfPN5OXWhSRDQmQMTSLrUt";
  }

  public static String attachments_path() {
    switch(play.Play.id){
      case "test": return "data/attachments";
      case "prod": return "data/attachments";
     }
    return "data/attachments";
  }

  public static String date_format() {
    switch(play.Play.id){
      case "test": return "yyyy-MM-dd";
      case "prod": return "yyyy-MM-dd";
     }
    return "yyyy-MM-dd";
  }

  public static String db_url() {
    switch(play.Play.id){
      case "test": return "jdbc:h2:mem:play;MODE";
      case "prod": throw new java.lang.RuntimeException("設定がありません：%" + play.Play.id + ".db.url");
     }
    throw new java.lang.RuntimeException("設定がありません：%" + play.Play.id + ".db.url");
  }

  public static String jpa_ddl() {
    switch(play.Play.id){
      case "test": return "create";
      case "prod": throw new java.lang.RuntimeException("設定がありません：%" + play.Play.id + ".jpa.ddl");
     }
    throw new java.lang.RuntimeException("設定がありません：%" + play.Play.id + ".jpa.ddl");
  }

  public static String mail_smtp() {
    switch(play.Play.id){
      case "test": return "mock";
      case "prod": return "mock";
     }
    return "mock";
  }
}
