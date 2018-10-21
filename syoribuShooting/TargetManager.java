package syoribuShooting;

import syoribuShooting.sprite.Target;
import syoribuShooting.system.Function;
import syoribuShooting.system.InputEventManager;
import syoribuShooting.system.TemporallyBool;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ConcurrentModificationException;
import java.util.ListIterator;

import static syoribuShooting.GameConfig.*;

public class TargetManager
{
    public static final int LOCAL_LIST     = 0;
    public static final int GLOBAL_LIST    = 1;
    public static final int ALL_LIST       = 2;

    private TargetList[] targetList = new TargetList[ALL_LIST];

    private TargetEventListener targetEventListener;

    private TemporallyBool isCursorTouching = new TemporallyBool();

    public TargetManager(TargetEventListener listener, TargetList localList)
    {
        this.targetEventListener = listener;
        setLocalList(localList);
        setGlobalList(new TargetList());
    }

    public void update(int elapsedTime)
    {
        InputEventManager eventManager = Main.getEventManager();
        boolean cursorTouched = false;

        for (TargetList list : targetList)
        {
            for(ListIterator<Target> it = list.listIterator(); it.hasNext();)
            {
                final Target target = it.next();
                target.update(elapsedTime);

                // マウスカーソルが的に触れていればフラグを立てる
                if (target.isClickable() && target.getBounds().isContain(eventManager.mouseX(), eventManager.mouseY()))
                {
                    cursorTouched = true;
                }

                // 画面外になったらstateをDISPOSEに変更
                if (isOuter(target))
                {
                    target.setState(Target.State.DISPOSE);
                }

                // stateがDISPOSEならリストから消す
                if (target.getState() == Target.State.DISPOSE)
                {
                    try {
                        it.remove();
                    } catch (ConcurrentModificationException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // イベントの発火
        isCursorTouching.update(cursorTouched);
        if(isCursorTouching.isUp()) {
            targetEventListener.cursorEnteredTarget();
        }
        else if (isCursorTouching.isDown()) {
            targetEventListener.cursorExitedTarget();
        }

        // 的クリック当たり判定
        checkHit(eventManager);

    }

    public void draw(Graphics2D g2d)
    {
        for (TargetList list : targetList)
        {
            for(ListIterator<Target> it = list.listIterator(); it.hasNext();) {
                final Target target = it.next();
                target.draw(g2d);
            }
        }
    }

    public TargetList getLocalList()
    {
        return targetList[LOCAL_LIST];
    }

    public TargetList getGlobalList()
    {
        return targetList[GLOBAL_LIST];
    }

    public void setLocalList(TargetList list)
    {
        targetList[LOCAL_LIST] = list;
    }

    private void setGlobalList(TargetList list)
    {
        targetList[GLOBAL_LIST] = list;
    }

    private void setAllState(Target.State state)
    {
        for (TargetList list : targetList) {
            for (Target target : list) {
                target.setState(state);
            }
        }
    }

    public void forEach(final int listType, Function<? extends Target> func)
    {
        switch (listType) {
            case LOCAL_LIST:
                for(Target t : targetList[LOCAL_LIST]) {
                    func.task(t);
                }
                break;

            case GLOBAL_LIST:
                for(Target t : targetList[GLOBAL_LIST]) {
                    func.task(t);
                }
                break;

            case ALL_LIST:
                for(Target t : targetList[LOCAL_LIST]) {
                    func.task(t);
                }
                for(Target t : targetList[LOCAL_LIST]) {
                    func.task(t);
                }
                break;

            default:
                throw new IllegalArgumentException("Argument " + listType + " is Illegal");
        }
    }

    public boolean isEmpty(final int listType)
    {
        switch (listType) {
            case LOCAL_LIST:
                return getLocalList().isEmpty();

            case GLOBAL_LIST:
                return getGlobalList().isEmpty();

            case ALL_LIST:
                return getLocalList().isEmpty() && getGlobalList().isEmpty();

            default:
                throw new IllegalArgumentException("Argument " + listType + " is Illegal");
        }
    }

    private void checkHit(InputEventManager eventManager)
    {
        if (!eventManager.justNowMousePressed(MouseEvent.BUTTON1)) {
            return;
        }

        int px = eventManager.mousePressedX();
        int py = eventManager.mousePressedY();

        for (TargetList list : targetList) {
            for (ListIterator<Target> it = list.listIterator(list.size()); it.hasPrevious(); )
            {
                final Target elem = it.previous();
                if (elem.isClickable() && elem.getBounds().isContain(px, py))
                {
                    targetEventListener.clickedTarget(new TargetEvent(elem, px, py ));
                    return;
                }
            }
        }
    }

    private static boolean isOuter(Target t)
    {
        final int x = ((int) t.getXdefault());
        final int y = ((int) t.getYdefault());
        return (x < OUTER_WINDOW_MINUS
                || y < OUTER_WINDOW_MINUS
                || OUTER_WINDOW_PLUS < x
                || OUTER_WINDOW_PLUS < y);
    }
}

