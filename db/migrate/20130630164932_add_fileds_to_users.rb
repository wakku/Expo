class AddFiledsToUsers < ActiveRecord::Migration
  def change
    add_column :users, :name, :string
    add_column :users, :email, :string
    add_column :users, :password_digest, :string
    add_column :users, :remember_token, :string
    add_index  :users, :remember_token
    add_column :users, :admin, :boolean, default: false
  end
end
