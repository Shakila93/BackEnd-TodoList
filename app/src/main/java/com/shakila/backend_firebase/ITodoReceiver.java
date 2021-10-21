package com.shakila.backend_firebase;

import java.util.List;

public interface ITodoReceiver {
    void receive(List<ListItem> TodoList);
}
