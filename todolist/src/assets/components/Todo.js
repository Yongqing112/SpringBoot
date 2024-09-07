import React from 'react';
import TitleBox from './TitleBox'
import TodoForm from './TodoForm'
import TodoItems from './TodoItems'

const ToDoList = () => {
    return (
        <div className="container">
            <TitleBox/>
            <div className="todo-box">
                <TodoForm/>
                <TodoItems/>
            </div>
        </div>
    );
};

export default ToDoList;