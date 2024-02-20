package org.example;

import net.thauvin.erik.crypto.CryptoPrice;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MyBot extends TelegramLongPollingBot {

    public MyBot() {
        super("6504725857:AAHLXd2XEECOP3PCFsqkqVDSR8iMlLPQnzw");
    }

    @Override
    public void onUpdateReceived(Update update) {
        var chatId = update.getMessage().getChatId();
        var text = update.getMessage().getText();

        try {

        if (text.equals("/start")) {
            sendMessage(chatId, "Hello, welcome!");
        } else if (text.equals("btc")) {
            sendPicture(chatId, "btc.png");
            sendPrice(chatId, "BTC");

        } else if (text.equals("eth")) {
            sendPicture(chatId, "eth.png");
            sendPrice(chatId, "ETH");

        } else if (text.equals("bnb")) {
            sendPicture(chatId, "bnb.png");
            sendPrice(chatId, "BNB");

        } else if (text.equals("usdt")) {
            sendPicture(chatId, "usdt.png");
            sendPrice(chatId, "USDT");

        } else if (text.equals("doge")) {
            sendPicture(chatId, "doge.png");
            sendPrice(chatId, "DOGE");

        } else if (text.equals("btc,eth")) {
            sendPrice(chatId, "BTC");
            sendPrice(chatId, "ETH");

        } else if (text.equals("eos")) {
            sendPicture(chatId, "eos.png");
            sendPrice(chatId, "EOS");

        } else if (text.equals("eth,btc,doge")) {
            sendPrice(chatId, "ETH");
            sendPrice(chatId, "BTC");
            sendPrice(chatId, "DOGE");

        } else if (text.equals("all")) {
            sendPrice(chatId, "BTC");
            sendPrice(chatId, "ETH");
            sendPrice(chatId, "BNB");
            sendPrice(chatId, "USDT");
            sendPrice(chatId, "EOS");
            sendPrice(chatId, "DOGE");

        } else if (text.matches("[a-zA-Z]+\\d+")) {
                // Извлекаем символьную часть и числовую часть из команды
            String currencySymbol = text.replaceAll("\\d", "");
            double amountInDollars = Double.parseDouble(text.replaceAll("[^\\d.]", ""));

                // Рассчитываем количество криптовалюты, которую можно купить
            double cryptoAmount = calculateCryptoAmount(currencySymbol, amountInDollars);

                // Отправляем результат пользователю
            sendMessage(chatId, "For " + amountInDollars + " USD, you can buy approximately " +
                        cryptoAmount + " " + currencySymbol + ".");

        } else {
            sendMessage(chatId, "Unknown command!");
        }

        } catch (Exception e) {
            System.out.println("Error!");
        }
    }

    void sendPrice(long chatId, String name) throws  Exception {
        var price = CryptoPrice.spotPrice(name);
        sendMessage(chatId, name + " price: " + price.getAmount().doubleValue());

    }
    void sendPicture(long chatId, String name) throws Exception {
        var photo = getClass().getClassLoader().getResourceAsStream(name);
        var picture = new SendPhoto();
        picture.setChatId(chatId);
        picture.setPhoto(new InputFile(photo, name));
        execute(picture);
    }

    void sendMessage(long chatId, String text) throws Exception {
        var massage = new SendMessage();
        massage.setChatId(chatId);
        massage.setText(text);
        execute(massage);
    }

    double calculateCryptoAmount(String currencySymbol, double amountInDollars) throws Exception {
        var price = CryptoPrice.spotPrice(currencySymbol);

        double cryptoAmount = amountInDollars / price.getAmount().doubleValue();

        return cryptoAmount;
    }

    @Override
    public String getBotUsername() {
        return "SoNat1_bot";
    }
}
