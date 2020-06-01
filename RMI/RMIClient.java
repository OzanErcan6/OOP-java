import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RMIClient {


    private static IMazeHub mazeHub;

    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException{

        int index=0;
        int num1=0;
        int num2=0;
        int num3=0;

        Scanner scanner = new Scanner(System.in);
        ParsedInput parsedInput = null;
        String input;
        while( true ) {
            mazeHub = (IMazeHub) Naming.lookup("//localhost/MyServer");

            input = scanner.nextLine();
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(input);
            String string="";


            try {
                parsedInput = ParsedInput.parse(input);
            }
            catch (Exception ex) {
                parsedInput = null;
            }
            if ( parsedInput == null ) {
                System.out.println("Wrong input format. Try again.");
                continue;
            }

            switch(parsedInput.getType()) {
                case CREATE_MAZE:
                    m.find();
                    num1=Integer.parseInt(m.group());
                    m.find();
                    num2=Integer.parseInt(m.group());

                    mazeHub.createMaze(num1,num2);
                    break;
                case DELETE_MAZE:
                    mazeHub.removeMaze(index);
                    break;
                case SELECT_MAZE:
                    m.find();
                    num1=Integer.parseInt(m.group());

                    if(mazeHub.getNumberOfMaze()-1 < num1){
                        System.out.println("Operation Failed.");
                        break;
                    }
                    else System.out.println("Operation Success.");


                    index=num1;

                    break;
                case PRINT_MAZE:
                    mazeHub.Serverprint(index);
                    break;
                case CREATE_OBJECT:
                    m.find();
                    num1=Integer.parseInt(m.group());
                    m.find();
                    num2=Integer.parseInt(m.group());

                    String [] words = input.split(" ", 4);

                    for (String word : words){
                        string=word;
                    }


                    if(string.equals("wall"))
                        if (mazeHub.ServercreateObject(new Position(num1,num2),MazeObjectType.WALL,index))
                            System.out.println("Operation Success.");
                        else System.out.println("Operation Failed.");
                    if(string.equals("agent"))
                        if (mazeHub.ServercreateObject(new Position(num1,num2),MazeObjectType.AGENT,index))
                            System.out.println("Operation Success.");
                        else System.out.println("Operation Failed.");
                    if(string.equals("gold"))
                        if (mazeHub.ServercreateObject(new Position(num1,num2),MazeObjectType.GOLD,index))
                            System.out.println("Operation Success.");
                        else System.out.println("Operation Failed.");
                    if(string.equals("hole"))
                        if (mazeHub.ServercreateObject(new Position(num1,num2),MazeObjectType.HOLE,index))
                            System.out.println("Operation Success.");
                        else System.out.println("Operation Failed.");
                    break;

                case DELETE_OBJECT:

                    m.find();
                    num1=Integer.parseInt(m.group());
                    m.find();
                    num2=Integer.parseInt(m.group());

                    if (mazeHub.ServerdeleteObject(new Position(num1, num2), index))
                         System.out.println("Operation Success.");

                    else System.out.println("Operation Failed.");

                    break;
                case LIST_AGENTS:

                    mazeHub.ServergetAgents(0).forEach((n) -> System.out.println("Agent"+n.getId() +" at ("+ n.getPosition().getX()+","+n.getPosition().getY()+"). Gold collected: "+n.getCollectedGold() ));

                    break;
                case MOVE_AGENT:
                    m.find();
                    num1=Integer.parseInt(m.group());
                    m.find();
                    num2=Integer.parseInt(m.group());
                    m.find();
                    num3=Integer.parseInt(m.group());

                    if (mazeHub.ServermoveAgent(num1,new Position(num2,num3),index))
                        System.out.println("Operation Success.");

                    else System.out.println("Operation Failed.");



                    break;
                case QUIT:
                    return;
            }
        }
    }
}
