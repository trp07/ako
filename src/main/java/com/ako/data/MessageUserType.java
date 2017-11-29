package com.ako.data;

public enum MessageUserType {
	FROM(1), TO(2), CC(3), BCC(4);
 
    private int type;
 
    private MessageUserType(int type) {
        this.type = type;
    }
    
    public static MessageUserType getType(Integer id) {
        
        if (id == null) {
            return null;
        }

        for (MessageUserType type : MessageUserType.values()) {
            if (id.equals(type.getType())) {
                return type;
            }
        }
        
        throw new IllegalArgumentException("No matching MessageUserType for id " + id);
    }
    
 
    public int getType() {
        return type;
    }
}
