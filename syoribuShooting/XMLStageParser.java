package syoribuShooting;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import syoribuShooting.sprite.LinearMotion;
import syoribuShooting.sprite.Motion;
import syoribuShooting.sprite.QuietMotion;
import syoribuShooting.sprite.Target;
import syoribuShooting.stage.BaseStage;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLStageParser extends DefaultHandler
{
    private static final String TAG_STAGE  = "stage";
    private static final String TAG_TARGET  = "target";
    private static final String TAG_APPEAR  = "appear";
    private static final String TAG_LINEAR_MOTION  = "linearMotion";
    private static final String TAG_QUIET_MOTION  = "quietMotion";

    private static final String ATTR_TYPE   = "type";
    private static final String ATTR_X      = "x";
    private static final String ATTR_Y      = "y";
    private static final String ATTR_SPEED  = "speed";
    private static final String ATTR_DELAY  = "delay";
    private static final String ATTR_ACCELERATION  = "acceleration";
    private static final String ATTR_TIMELIMIT = "timeLimit";
    private static final String ATTR_STAGE_ID = "stageID";

    private List<Target> list;
    private StringBuilder sb;
    private Target nowTarget;
    private BaseStage stage;

    public XMLStageParser(InputStream is)
    {
        if (is == null) throw new IllegalArgumentException("InputStream is null!!!");
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

    public BaseStage getParsedStage()
    {
        return this.stage;
    }

    private int parseValue(final String name, String val, int defaultVal)
    {
        int ret;
        if (val == null) return defaultVal;

        if (val.charAt(0) == '$') {
            val = val.substring(1);
        }
        val = val.toLowerCase();
        switch (val) {
            case "inf": ret = GameConfig.OUTER_WINDOW_PLUS; break;
            case "minf": ret = GameConfig.OUTER_WINDOW_MINUS; break;
            case "keep": ret = targetValueByName(this.nowTarget, name); break;

            case "all":
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

            default:
                try {
                    ret = Integer.parseInt(val);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    ret = defaultVal;
                }
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
        System.out.println("\nstartElement: " + qName);
        for (int i = 0; i < attributes.getLength(); i++) {
            System.out.println("attr " + attributes.getQName(i) + " = " + attributes.getValue(i));
        }
        switch (qName)
        {
            case TAG_STAGE: tagStage(qName, attributes);break;

            case TAG_TARGET:
                tagTarget(qName, attributes); break;

            case TAG_APPEAR:
                tagAppear(qName, attributes); break;

            case TAG_LINEAR_MOTION:
                tagLinearMotion(qName, attributes); break;

            case TAG_QUIET_MOTION:
                tagQuietMotion(qName, attributes); break;
        }
    }

    private void tagTarget(String qName, Attributes attr)
    {
        final String typeStr = attr.getValue(ATTR_TYPE);
        TargetType type = TargetType.rankC;
        if(typeStr != null) {
            if (typeStr.equals("rank?"))
                type = GameConfig.randomTargetType();
            else
                type = TargetType.valueOf(typeStr);
        }
        nowTarget = TargetFactory.createTarget(type);
    }

    private void tagAppear(String qName, Attributes attr)
    {
        int x=0, y=0, delay=0;
        for (int i = 0; i < attr.getLength(); i++) {
            final String name = attr.getQName(i);
            final String value = attr.getValue(i);
            switch (name) {
                case ATTR_X: x = parseValue(ATTR_X, value, 0); break;
                case ATTR_Y: y = parseValue(ATTR_Y, value, 0); break;
                case ATTR_DELAY: delay = parseValue(ATTR_DELAY, value, 0); break;
            }
        }
        this.nowTarget.setZoomDelay(delay);
        this.nowTarget.setXdefault(x);
        this.nowTarget.setYdefault(y);
    }

    private void tagMotion(Motion motion, String qName, Attributes attr)
    {
        int delay=0;
        double speed = 0.5, acceleration = 0.0;

        for (int i = 0; i < attr.getLength(); i++)
        {
            final String value = attr.getValue(i);
            switch (attr.getQName(i)) {
                case ATTR_DELAY: delay = parseValue(ATTR_DELAY, value, 0); break;
                case ATTR_SPEED: speed = Double.parseDouble(value); break;
                case ATTR_ACCELERATION: acceleration = Double.parseDouble(value); break;
            }
        }
        motion.setStartDelay(delay);
        motion.setSpeed(speed);
        motion.setAcceleration(acceleration);
    }

    private void tagLinearMotion(String qName, Attributes attr)
    {
        // Motionクラス共通の設定
        LinearMotion motion = new LinearMotion(nowTarget, 1.0);
        tagMotion(motion, qName, attr);

        int toX=0, toY=0;

        for (int i = 0; i < attr.getLength(); i++) {
            final String value = attr.getValue(i);
            switch (attr.getQName(i)) {
                case ATTR_X: toX = parseValue(ATTR_X, value, 0); break;
                case ATTR_Y: toY = parseValue(ATTR_Y, value, 0); break;
            }
        }
        motion.setPoints(toX, toY);
        nowTarget.setMotion(motion);
    }

    private void tagQuietMotion(String qName, Attributes attr) throws SAXException
    {
        int aliveTime = parseValue(ATTR_TIMELIMIT, attr.getValue(ATTR_TIMELIMIT), -1);
        if (aliveTime < 0) throw new SAXException();
        this.nowTarget.setMotion(new QuietMotion(nowTarget, aliveTime));
    }

    private void tagStage(String qName, Attributes attr) throws SAXException
    {
        final int timeLimit = parseValue(ATTR_TIMELIMIT, attr.getValue(ATTR_TIMELIMIT), 1000);
        final int stageID = parseValue(ATTR_STAGE_ID, attr.getValue(ATTR_STAGE_ID), -1);

        if (stageID < 0) throw new SAXException();

        this.stage = new BaseStage(stageID)
        {
            @Override
            public int getTimeLimit()
            {
                return timeLimit;
            }

            @Override
            public boolean shouldBeFinished()
            {
                return this.stopWatch.getElapsed() >= this.getTimeLimit() || noTargets();
            }

            @Override
            protected void _init() {}

            @Override
            protected void _update(Game game) {}
        };
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        switch (qName)
        {
            case TAG_TARGET:
                System.out.println("XMLstage.add: " + nowTarget);
                stage.getTargetList().add(nowTarget);
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
        System.out.println("\n-------------------XML Document Start--------------------");
    }

    @Override
    public void endDocument() throws SAXException
    {
        System.out.println("----------------------XML Document End----------------------\n");
    }

}
