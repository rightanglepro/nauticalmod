package ml.rightangle.nauticalmod;

/**
 *
 * @author RightAnglePro
 */
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
    
    public static void main(String[] args) throws LoginException {
        JDA bot = JDABuilder.createDefault("token") // token
                .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS) // enable privileged intents
                .setActivity(Activity.playing("chess")) // user activity (ex: Playing (string))
                .addEventListeners(new NauticalMod())
                .build();
        System.out.println("NauticalMod is now running");
        
    }
    
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        //commands
        if (event.getMessage().getContentRaw().substring(0, botPrefix.length()).equals(botPrefix)) { // if the message starts with the bot prefix,
            System.out.println(event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator() + ": " + event.getMessage().getContentRaw());
            
            if (event.getMessage().getContentRaw().substring(botPrefix.length()).equals("ping")) {
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
            
            else if (event.getMessage().getContentRaw().substring(botPrefix.length()).equals("help")) {
                event.getChannel().sendMessage("**NauticalMod Help**\n\n" +
                                               "**Prefix:** " + botPrefix + "\n\n" +
                                               "**Commands**\n" +
                                               "**" + botPrefix + "help** - You are here! The help menu.\n" +
                                               "**" + botPrefix + "ping** - NauticalMod's ping/latency.\n" +
                                               "**" + botPrefix + "killtheweebs** - Use the Anime Removal Kit.\n" +
                                               "\n" +
                                               "**About**\n" +
                                               "NauticalMod Discord Bot\n" +
                                               "v2.1 Alpha by Right Angle Productions\n" +
                                               "(c) 2023\n" +
                                               "<https://github.com/rightanglepro/nauticalmod>").queue();
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
