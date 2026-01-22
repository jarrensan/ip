import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Duke {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String h_line = "\n____________________________________________________________\n";
        String greeting = " Hello! I'm JellyBot\n What can I do for you?";
        String bye = " Bye. Hope to see you again soon!";
        ArrayList<String> stringList = new ArrayList<>();

        System.out.println(h_line + greeting + h_line);

        String input = br.readLine();
        while (!input.equals("bye")) {
            if (input.equals("list")) {
                String list_out = "";
                for (int i = 0; i < stringList.size(); i++) {
                    list_out += (i+1 + ". " + stringList.get(i) + "\n");
                }
                System.out.println(h_line + list_out + h_line);
            } else {
                stringList.add(input);
                System.out.println(h_line + " " + input + h_line);
            }
            input = br.readLine();
        }

        System.out.println(h_line + bye + h_line);
    }
}
