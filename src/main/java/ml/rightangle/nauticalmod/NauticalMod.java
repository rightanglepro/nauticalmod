/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package ml.rightangle.nauticalmod;

/**
 *
 * @author RightAnglePro
 */
import java.util.Date;
import java.util.TimeZone;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.jetbrains.annotations.NotNull;

public class NauticalMod extends ListenerAdapter {
    
    String botPrefix = "nm:";
    
    static long timeNMStarted;
    
    public static void main(String[] args) throws LoginException {
        JDA bot = JDABuilder.createDefault("token") // token
                .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS) // enable privileged intents
                .setActivity(Activity.playing("chess")) // user activity (ex: Playing (string))
                .addEventListeners(new NauticalMod())
                .build();
        System.out.println("NauticalMod is now running");
        
        timeNMStarted = System.currentTimeMillis();
        
    }
    
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        //commands
        if (event.getMessage().getContentRaw().substring(0, botPrefix.length()).equals(botPrefix)) { // if the message starts with the bot prefix,
            String command = event.getMessage().getContentRaw().substring(botPrefix.length());
            
            System.out.println(event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator() + ": " + event.getMessage().getContentRaw());
            
            if (command.equals("ping")) {
                long ping = event.getJDA().getGatewayPing();
                
                String[] pingResponse = {"h", 
                                         "Its a nice day today, huh?", 
                                         "Time is not real", 
                                         "pumpkin joj", 
                                         "It was a one time thing I swear", 
                                         ":thermometer: The current temperature outside is -409 degrees Fahrenheit.", 
                                         "Hatsune Miku", 
                                         "ごきげんよう", 
                                         "where am i", 
                                         "Sent from my iPhone", 
                                         ":telephone: Ring Ring! Just kidding, there's no one at the userphone.", 
                                         "https://cdn.discordapp.com/attachments/746165864432140350/768651646967349258/animeremovalkit.png", 
                                         "abanchoo"};
                int pingResponseInd = (int)((Math.random() * (pingResponse.length - 1)) + 0.5);
                
                event.getChannel().sendMessage(":large_blue_diamond: Pong! " + pingResponse[pingResponseInd] + "\n" + ping + " milliseconds").queue();
            }
            
            else if (command.equals("help")) {
                event.getChannel().sendMessage("**NauticalMod Help**\n\n" +
                                               "**Prefix:** " + botPrefix + "\n" +
                                               "\n" +
                                               "**Commands**\n" +
                                               "**" + botPrefix + "help** - You are here! The help menu.\n" +
                                               "**" + botPrefix + "ping** - NauticalMod's ping/latency.\n" +
                                               "**" + botPrefix + "time** *(timezone)* - The current time.\n" +
                                               "**" + botPrefix + "uptime** - Amount of time NauticalMod has been running.\n" +
                                               "**" + botPrefix + "killtheweebs** - Use the Anime Removal Kit.\n" +
                                               "\n" +
                                               "**About**\n" +
                                               "NauticalMod Discord Bot\n" +
                                               "v2.1 Alpha 2 by Right Angle Productions\n" +
                                               "(c) 2023\n" +
                                               "<https://github.com/rightanglepro/nauticalmod>").queue();
            }
            
            else if (command.substring(0, 4).equals("time")) { //command in format "prefix:time (timezone)", ex. "prefix:time America/Chicago"
                String tzName;
                if (command.length() < 5){
                    tzName = "UTC";
                }
                else {
                    tzName = command.substring(5); //sets to timezone after "time "
                }
                TimeZone tz = TimeZone.getTimeZone(tzName);
                
                long timeSinceEpoch = System.currentTimeMillis() - TimeZone.getDefault().getOffset(System.currentTimeMillis()); //get time since Epoch by removing UTC offset in ms from System.currentTimeMillis()
                long timeInTZ = timeSinceEpoch + tz.getOffset(System.currentTimeMillis()); //get time in timezone by adding UTC offset in ms from tz to time since Epoch in ms
                Date currentTime = new Date(timeInTZ); //initialize date object with time in timezone
                String currentTimeStr = currentTime.toString(); //get string form of currentTime, ex. "Sat Jan 21 23:24:27 UTC 2023" (yes this is CST, NOT UTC)
                
                String dayWeek = currentTimeStr.substring(0,3); //ex. "Sat"
                String month = currentTimeStr.substring(4,7); //ex. "Jan"
                int day = Integer.parseInt( currentTimeStr.substring(8,10) ); //ex. "21"
                int year = Integer.parseInt( currentTimeStr.substring(24,28) ); //ex. "2023"
                int hour = Integer.parseInt( currentTimeStr.substring(11,13) ); //ex. "23"
                String minute = currentTimeStr.substring(14,16); //ex. "24"
                String second = currentTimeStr.substring(17,19); //ex. "27"
                
                //adjust hour for AM/PM
                String AMPM = "AM";
                if (hour > 12) { //hours 13-23
                    hour -= 12; //become their PM equivalent
                    AMPM = "PM"; //and are PM
                }
                else if (hour == 0) { //hour 0
                    hour = 12; //becomes 12 (AM)
                }
                
                event.getChannel().sendMessage(":large_blue_diamond: The time in **" + tz.getDisplayName() + "** is " + dayWeek + ", " + month + " " + day + ", " + year + " at " + hour + ":" + minute + ":" + second + " " + AMPM).queue();
            }
            
            else if (command.equals("uptime")) {
                long uptime = System.currentTimeMillis() - timeNMStarted;
                
                int milliseconds = (int)(uptime);
                int minutes = milliseconds / 60000;
                int hours = minutes / 60;
                int days = hours / 24;
                
                double seconds = (double)(milliseconds - minutes * 60000) / 1000;
                minutes = minutes - hours * 60;
                hours = hours - days * 24;
                
                event.getChannel().sendMessage(":large_blue_diamond: NauticalMod's uptime is " + days + "d " + hours + "h " + minutes + "m " + seconds + "s.").queue();
            }
            
            else if (event.getMessage().getContentRaw().substring(botPrefix.length()).equals("killtheweebs")) {
                event.getChannel().sendMessage(":large_blue_diamond: アニメ除去キット：ウィーブが殺されました。https://cdn.discordapp.com/attachments/746165864432140350/768651646967349258/animeremovalkit.png").queue();
            }
            
        }
        //other
        else if (event.getMessage().getContentRaw().equals("poop")) {
            event.getChannel().sendMessage(":flushed:").queue();
        }
        
    }
    
}
