package me.staartvin.simplesuffix.sqlite;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity()
@Table(name = "ss_database")
public class Database {

	    @Id
	    private int id;
	    
	    private String playerName;
	    
	    private String suffix;
	 
	    private String prefix;
	    
	    public void setId(int id) {
	        this.id = id;
	    }
	 
	    public int getId() {
	        return id;
	    }
	 
	    public String getSuffix() {
	        return suffix;
	    }
	 
	    public void setSuffix(String suffix) {
	        this.suffix = suffix;
	    }
	 
	    public String getPlayerName() {
	        return playerName;
	    }
	 
	    public void setPlayerName(String ply) {
	        this.playerName = ply;
	    }

		public String getPrefix() {
			return prefix;
		}

		public void setPrefix(String prefix) {
			this.prefix = prefix;
		}
	}
