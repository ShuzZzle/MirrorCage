package action;

import java.util.Arrays;

import org.newdawn.slick.command.BasicCommand;

/**
 * The action command class defines an action either with parameters or no one
 * Somehow there is a weird error in lua and thats why you have to write
 * all arguments for the constructor explicitly. Note that you can use any amount of parameters
 * if you call the constructor out of the java environment. :)
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public class ActionCommand extends BasicCommand {
 
 /* Attributes */
 private Object[] args; // the arguments passed to this command

 /**
  * Constructor 1
  * @param name
  */
 public ActionCommand(String name) {
  super(name);
  args = null;
 }
 
 /**
  * Constructor 2
  * @param name
  */
 public ActionCommand(String name, Object arg1){
  super(name);

  args = new Object[1];
  args[0] = arg1;
 }
 
 /**
  * Constructor 3
  * @param name
  */
 public ActionCommand(String name, Object arg1, Object arg2){
  super(name);

  args = new Object[2];
  args[0] = arg1;
  args[1] = arg2;
 }
 
 /**
  * Constructor 4
  * @param name
  */
 public ActionCommand(String name, Object arg1, Object arg2, Object arg3){
  super(name);

  args = new Object[3];
  args[0] = arg1;
  args[1] = arg2;
  args[2] = arg3;
 }
 
 /**
  * Constructor 5
  * @param name
  */
 public ActionCommand(String name, Object arg1, Object arg2, Object arg3, Object arg4){
  super(name);

  args = new Object[4];
  args[0] = arg1;
  args[1] = arg2;
  args[2] = arg3;
  args[3] = arg4;
 }
 
 /**
  * Constructor 6
  * @param name
  * @param args
  */
 public ActionCommand(String name, Object... args) {
  super(name);
  this.args = args;
 }
 
 /**
  * Get the arguments
  * @return
  */
 public Object[] getArgs() {
  return args;
 }
 
 /**
  * Print the attributes
  */
 @Override
 public String toString() {
  return "ActionCommand [args=" + Arrays.toString(args) + "]";
 }
 
}