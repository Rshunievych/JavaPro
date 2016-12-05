package ChatClient;

import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		try {
			Utils.logIn(scanner);
	
			Thread th = new Thread(new GetThread());
			th.setDaemon(true);
			th.start();

            Utils.help();
            System.out.println("Enter your message: ");
            while (true) {
				String text = scanner.nextLine();
				if (text.isEmpty()){
					Utils.setStatus("OFFLINE");
					break;
				}
				if ("-users".equals(text)){
                    Utils.getUsers();
                    continue;
                }
				if ("-status".equals(text)){
					System.out.println("Enter your status: ");
					String status = scanner.nextLine();
					Utils.setStatus(status);
                    continue;
				}
				if ("-room".equals(text)){
					System.out.println("Enter room name");
					String room = scanner.nextLine();
					Utils.setRoom(room);
                    continue;
				}
				if("-help".equals(text)){
                    Utils.help();
                    continue;
                }
                if (text.equals("-pm")) {

                    System.out.println("Send to (enter username): ");
                    String to = scanner.nextLine();
                    System.out.println("Enter your message:");
                    text = scanner.nextLine();

                    Message m = new Message(Utils.getLogin(), to, text, "private message for " + to);

                    int res = m.send(Utils.getURL() + "/add");
                    if (res != 200) { // 200 OK
                        System.out.println("HTTP error occurred: " + res);
                        return;
                    }
                    continue;
                }

				Message m = new Message(Utils.getLogin(), "all", text, Utils.getRoom());
				int res = m.send(Utils.getURL() + "/add");

				if (res != 200) { // 200 OK
					System.out.println("HTTP error occurred: " + res);
					return;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			scanner.close();
		}
	}
}