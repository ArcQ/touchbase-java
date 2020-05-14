defmodule Caht.Repo do
  use Ecto.Repo,
    otp_app: :caht,
    adapter: Ecto.Adapters.Postgres
end
