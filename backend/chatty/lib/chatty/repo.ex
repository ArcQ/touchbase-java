defmodule Chatty.Repo do
  use Ecto.Repo,
    otp_app: :chatty,
    adapter: Ecto.Adapters.Postgres
end
