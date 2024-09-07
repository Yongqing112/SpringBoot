import React from 'react';

const TodoForm = () => {
    return (
        <form className="header">
            <input type="text" id="input" placeholder="New Item..." />
            <button type="submit" class="addBtn">
                Add
            </button>
        </form>
    )
}

export default TodoForm;