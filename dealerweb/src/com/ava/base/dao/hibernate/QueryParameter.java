package com.ava.base.dao.hibernate;

/**
 * for hibernate query,you can pass QueryParameter Object into TransMsg.
 */
public class QueryParameter implements Cloneable{
	
	/**	=	*/
	public final static String OPERATOR_EQ = "=";
	/**	>	*/
    public final static String OPERATOR_GT = ">";
    /**	<	*/
    public final static String OPERATOR_LT = "<";
    /**	>=	*/
    public final static String OPERATOR_GE = ">=";
    /**	<=	*/
    public final static String OPERATOR_LE = "<=";
    /**	<>	*/
    public final static String OPERATOR_NEQ ="<>";
    /**	like	*/
    public final static String OPERATOR_LIKE = "like";
    /**	in	*/
    public final static String OPERATOR_IN = "in";
    /**	not in	*/
    public final static String OPERATOR_NOT_IN = " not in";
    
	private String propertyName;
    private String parameterName;
    private String operator;
    private Object value;
    
    public QueryParameter(){}
    
    /**
     * propertyName = parameterName
     * @param propertyName
     * @param operator	-defined in this class
     * @param value
     */
    public QueryParameter(String propertyName,String operator,Object value){
        this.propertyName = propertyName;
        this.parameterName = propertyName;
        this.operator = operator;
        this.value = value;
    }
    
    public QueryParameter(String propertyName,String parameterName,String operator,Object value){
        this.propertyName = propertyName;
        this.parameterName = parameterName;
        this.operator = operator;
        this.value = value;
    }
    
     
    public String getOperator() {
        return operator;
    }
    public void setOperator(String operator) {
        this.operator = operator;
    }
    public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        this.value = value;
    }
    public String getParameterName() {
        return parameterName;
    }
    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }
    public String getPropertyName() {
        return propertyName;
    }
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
  
    @Override
	public Object clone(){
    	QueryParameter colneParameter = null;
		try {
			colneParameter = (QueryParameter)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
        return colneParameter;
     }
}
