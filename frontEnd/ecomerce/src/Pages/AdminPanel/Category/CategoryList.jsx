import React from 'react';
import { ArrayField, Datagrid, List, TextField } from "react-admin";

const CategoryList = () => {
  return (
    <List>
        <Datagrid>
            <TextField disabled source="id" />
            <TextField source="name" />
            <TextField source="code" />
            <TextField source="description" />
            <ArrayField source="categoryTypes">
                <Datagrid rowClick={false}>
                    <TextField disabled source="id" />
                    <TextField source="name" />
                    <TextField source="code" />
                    <TextField source="description" />
                </Datagrid>
            </ArrayField>
        </Datagrid>
    </List>
  )
}

export default CategoryList