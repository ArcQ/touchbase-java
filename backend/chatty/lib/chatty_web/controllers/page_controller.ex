defmodule ChattyWeb.PageController do
  use ChattyWeb, :controller

  def index(conn, _params) do
    render(conn, "index.html")
  end
end
