package View;

import Controllers.DatabaseController;
import Enums.MenuEnums;
import Model.User;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenu
{
    private DatabaseController databaseController;
    private User user;
    public MainMenu (DatabaseController databaseController, User user)
    {
        this.databaseController = databaseController;
        this.user = user;

    }

    public ArrayList<User> run (Scanner scanner)
    {
        String input;
        Matcher matcher;
        ArrayList<User > users = new ArrayList<>();
        while ( !(input = scanner.nextLine()).equals("menu exit"))
        {
            if ( (matcher = getCommandMatcher(input, MenuEnums.PLAYGAME.getRegex())).matches())
            {
                String username = matcher.group("username");
                users.add(this.databaseController.getUserByUsername(username));
                input = input.substring(19 + username.length() + 1);
                matcher = getCommandMatcher(input, "(--player\\d+|-p\\d+) (?<username>[A-Za-z0-9]+)");
                while (matcher.find())
                {
                    username = matcher.group("username");
                    users.add(this.databaseController.getUserByUsername(username));
                    input = input.substring(9 + 1 + username.length());
                }
                return users;
            }
            else if ((matcher = getCommandMatcher(input, MenuEnums.SHOWCURRENT.getRegex())).matches())
            {
                System.out.println("Main Menu");
            }
            else if ( ((matcher = getCommandMatcher(input, MenuEnums.ENTER.getRegex())).matches()))
            {
                String menuName = matcher.group("menuName");
                if (menuName.equals("Login"))
                {
                    return null;
                }
                else if (menuName.equals("Main"))
                {

                }
                else if (menuName.equals("Profile"))
                {
                    ProfileMenu profileMenu = new ProfileMenu (this.databaseController, this.user);
                    profileMenu.run(scanner);
                }
                else
                    System.out.println("invalid command");
            }
            else{
                System.out.println("invalid command");
            }
        }
        return null;
    }

    private Matcher getCommandMatcher(String input, String regex)
    {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }

}