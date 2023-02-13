package com.blinked.modules.core.events;

import org.springframework.lang.NonNull;

/**
 * PostComment new event.
 *
 * @author ssatwa
 * @date 19-4-23
 */
public class CommentNewEvent extends AbstractCommentBaseEvent {

    /**
     * Create a new ApplicationEvent.
     *
     * @param source    the object on which the event initially occurred (never {@code null})
     * @param commentId comment id
     */
    public CommentNewEvent(Object source, @NonNull Long commentId) {
        super(source, commentId);
    }
}
