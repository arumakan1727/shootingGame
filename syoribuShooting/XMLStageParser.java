package syoribuShooting;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import syoribuShooting.sprite.Target;
import syoribuShooting.stage.AbstractStage;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLStageParser extends DefaultHandler
{
    private static final String TAG_TARGET  = "target";
    private static final String TAG_APPEAR  = "appear";
    private static final String TAG_MOTION  = "motion";
    private static final String TAG_LINEAR_MOTION  = "linearMotion";
    private static final String ATTR_TYPE   = "type";
    private static final String ATTR_X      = "x";
    private static final String ATTR_Y      = "y";
    private static final String ATTR_SPEED  = "y";
    private static final String ATTR_DELAY  = "delay";
    private static final String ATTR_ACCELERATION  = "acceleration";

    private List<Target> list;
    private StringBuilder sb;
    private Target nowTarget;
    private AbstractStage stage;

    public XMLStageParser(InputStream is)
    {
        this.list = new ArrayList<>();
        this.sb = new StringBuilder();

        try {
            SAXParser xmlParser = SAXParserFactory.newInstance().newSAXParser();
            xmlParser.parse(is, this);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private int parseValue(final String name, String val, int defaultVal)
    {
        int ret = defaultVal;
        if (val == null) return defaultVal;

        if (val.charAt(0) == '$')
        {
            val = val.substring(1).toLowerCase();
            switch (val) {
                case "inf": ret = GameConfig.OUTER_WINDOW_PLUS; break;
                case "minf": ret = GameConfig.OUTER_WINDOW_MINUS; break;
                case "keep": ret = targetValueByName(this.nowTarget, name); break;

                case "bottom":
                case "top":
                case "left":
                case "right":
                case "center":
                    if (name.charAt(0) == 'x') {
                        ret = GameConfig.randomX(GameConfig.Allocation.valueOf(val.toUpperCase()));
                    } else {
                        ret = GameConfig.randomY(GameConfig.Allocation.valueOf(val.toUpperCase()));
                    }
                    break;
            }
        }
        else
        {
            ret = Integer.parseInt(val);
        }

        return ret;
    }

    private int targetValueByName(final Target t, final String name)
    {
        switch (name)
        {
            case "x": return (int)t.getXdefault();
            case "y": return (int)t.getYdefault();
            default: return 0;
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        switch (qName)
        {
            case TAG_TARGET:
            {
                final String attrVal = attributes.getValue(ATTR_TYPE);
                TargetFactory.TargetType type = TargetFactory.TargetType.rankC;
                if(attrVal != null) {
                    type = TargetFactory.TargetType.valueOf(attrVal);
                }
                nowTarget = TargetFactory.createTarget(type);
            } break;

            case TAG_APPEAR:
            {
                int x=0, y=0, delay=0;
                for (int i = 0; i < attributes.getLength(); i++) {
                    final String name = attributes.getQName(i);
                    final String value = attributes.getValue(i);
                    switch (name) {
                        case ATTR_X: x = parseValue(ATTR_X, value, 0); break;
                        case ATTR_Y: y = parseValue(ATTR_Y, value, 0); break;
                        case ATTR_DELAY: delay = parseValue(ATTR_DELAY, value, 0); break;
                    }
                }
                this.nowTarget.setDelay(delay);
                this.nowTarget.setXdefault(x);
                this.nowTarget.setYdefault(y);
            } break;

            case TAG_MOTION:
            {

            } break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        switch (qName)
        {
            case TAG_TARGET:
                break;
            case TAG_APPEAR:
                break;
            case TAG_MOTION:
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        super.characters(ch, start, length);
    }

    @Override
    public void processingInstruction(String target, String data) throws SAXException
    {
        super.processingInstruction(target, data);
    }

    @Override
    public void startDocument() throws SAXException
    {
        System.out.println("XML Document Start");
    }

    @Override
    public void endDocument() throws SAXException
    {
        System.out.println("XML Document End");
    }

}
