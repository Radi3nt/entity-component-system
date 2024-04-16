package fr.radi3nt.ecs.loadable.json.parser.values.parsers.number;

import fr.radi3nt.ecs.loadable.json.exceptions.JsonComponentParseException;
import fr.radi3nt.ecs.loadable.json.parser.values.parsers.JsonValueParser;
import fr.radi3nt.json.JsonValue;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public abstract class NumberJsonValueParser implements JsonValueParser {

    protected NumberJsonValueParser() {
    }

    protected Number getNumber(JsonValue value, String path) throws JsonComponentParseException {
        String number = value.toString();
        Number numberValue;
        try {
            numberValue = NumberFormat.getInstance(Locale.ENGLISH).parse(number);
        } catch (ParseException e) {
            throw new JsonComponentParseException("Could not parse number at location '" + path + "'", e);
        }
        return numberValue;
    }
}
