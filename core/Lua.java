/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

import java.util.HashMap;

import org.luaj.vm2.*;
import org.luaj.vm2.lib.jse.*;

/**
 * Lua interface to call methods which are embedded in the .lua files
 * This class is important for live coding.
 * @author Bengt, Marlo, Alexander, Niclas
 */
public final class Lua {

	/* Attributes */
	private static HashMap <String, Object> globals = new HashMap<>(); // global variables in the lua environment
    private LuaValue _G; // Bridge between Lua and Java

    /**
     * Instantiates a new lua script manager.
     */
    public Lua(){
        
    	_G = JsePlatform.standardGlobals(); // Get the bridge
    }
  
    /**
     * Execute the file
     * @param scriptName 
     */
    public void doFile(String scriptName){   
    	
    	try {
    		_G.get("dofile").call(LuaValue.valueOf(scriptName)); 
    	}
    	catch(Exception e){
    		e.printStackTrace();	
    	}
    }
    
    /**
     * Call a function from lua
     * @param functionName
     * @param values
     */   
    public void call(String functionName, Object value){
    	     
    	/* Get the parameters as lua values */
        LuaValue val = CoerceJavaToLua.coerce(value); // parse the parameters
        
        /* Get the function in the corresponding lua file */
        LuaValue function = _G.get(functionName);
        
        /* Call the function */
    	try {		
    		/* Varargs dist  = */ function.invoke(val);
    	}
    	catch(Exception e){
    		System.out.print("ERROR: Failed to do '" + functionName + "' - ");
    		e.printStackTrace();	
    	} 

    }
    
    /**
     * Call a function from lua
     * @param functionName
     * @param values
     */   
    public void call(String functionName, Object... values){
    	     
    	/* Get the parameters as lua values */
        LuaValue[] val = new LuaValue[values.length];
        
        for(int i = values.length; i-- > 0;)
        	val[i] = CoerceJavaToLua.coerce(values[i]); // parse the parameters
        
        /* Get the function in the corresponding lua file */
        LuaValue function = _G.get(functionName);
        
        /* Call the function */
    	try {		
    		/* Varargs dist  = */ function.invoke(LuaValue.varargsOf(val));
    	}
    	catch(Exception e){
    		System.out.print("ERROR: Failed to do '" + functionName + "' - ");
    		e.printStackTrace();	
    	} 

    }
    
    /**
     * Get the globals stored in the Lua environment
     * @return
     */
    public static HashMap <String, Object> getGlobals(){
    	
    	return globals;
    }
}