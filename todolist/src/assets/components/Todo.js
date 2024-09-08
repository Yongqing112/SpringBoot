import React from 'react';
import TitleBox from './TitleBox'
import TodoForm from './TodoForm'
import TodoItems from './TodoItems'
import { todosService } from '../../service/todo.js';
import { useState, useEffect } from 'react';

const ToDoList = () => {
    const [todos, setTodos] = useState([]);

    useEffect(() => {
        // 頁面載入時，取得代辦事項列表
        todosService.getTodos().then((data) => {
            setTodos(data);
        });
    }, []);

    const handleAdd = (text) => {
	        // 接收從TodoForm 呼叫的handleAdd()方法
        const data = {
            task: text,
        };
        todosService.createTodos(data).then((res) => {
            data.id = res;
            data.status = res;
            todos.push(data);
            setTodos([...todos]);
        });
    };

    const handleUpdate = (id, data) => {
        // 接收從TodoItems 呼叫的handleUpdate()方法
        todosService.updateTodos(id, data).then((res) => {
            const mapTodos = todos.map((todo) => {
                if (todo.id === id) {
                    todo.status = data.status;
                }
                return todo;
            });
            setTodos(mapTodos);
        });
    };

    const handleDelete = (id) => {
				// 接收從TodoItems 呼叫的handleDelete()方法
        todosService.deleteTodos(id).then((res) => {
            todos.forEach((todo, index) => {
                if (todo.id === id) {
                    todos.splice(index, 1);
                }
            });
            setTodos([...todos]);
        });
    };

    return (
        <div className="container">
            <TitleBox />
            <div className="todo-box">
                <TodoForm handleAdd={handleAdd} />
                <TodoItems todos={todos} handleUpdate={handleUpdate} handleDelete={handleDelete} />
            </div>
        </div>
    );
};

export default ToDoList;