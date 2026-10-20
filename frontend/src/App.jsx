import { useEffect, useState } from 'react'

function App() {
  const [data, setData] = useState(null)
  const [error, setError] = useState(null)

  useEffect(() => {
    fetch('/api/message')
      .then((res) => {
        if (!res.ok) throw new Error(`Request failed with status ${res.status}`)
        return res.json()
      })
      .then(setData)
      .catch((err) => setError(err.message))
  }, [])

  return (
    <main className="container">
      {error && <p className="error">Could not reach the backend: {error}</p>}

      {!error && !data && <p className="muted">Loading…</p>}

      {data && (
        <>
          <h1 className="message">{data.message}</h1>
          <div className="meta">
            <span>Version: {data.version}</span>
            <span>Server time: {new Date(data.serverTime).toLocaleString()}</span>
          </div>
        </>
      )}
    </main>
  )
}

export default App
